package com.iadv.cti.avaya.controller;

import com.iadv.cti.avaya.model.Agent;
import com.iadv.cti.avaya.model.Call;
import com.iadv.cti.avaya.service.AgentStateManager;
import com.iadv.cti.avaya.service.CallStateManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cti")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class CtiController {
    private final CallStateManager callManager;
    private final AgentStateManager agentManager;

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("timestamp", Instant.now().toString());
        health.put("calls", callManager.getActiveCallCount());
        health.put("agents", agentManager.getAgentCount());
        health.put("uptime", System.currentTimeMillis());

        log.debug("Health check: {} calls, {} agents",
                callManager.getActiveCallCount(),
                agentManager.getAgentCount());

        return ResponseEntity.ok(health);
    }

    @GetMapping("/calls/active")
    public ResponseEntity<List<Call>> getActiveCalls() {
        List<Call> calls = callManager.getActiveCalls();
        log.debug("📋 Retornando {} llamadas activas", calls.size());
        return ResponseEntity.ok(calls);
    }

    @GetMapping("/agents")
    public ResponseEntity<List<Agent>> getAgents() {
        List<Agent> agents = agentManager.getAgents();
        log.debug("👤 Retornando {} agentes", agents.size());
        return ResponseEntity.ok(agents);
    }

    @GetMapping("/extensions")
    public ResponseEntity<Map<String, String>> getExtensions() {
        Map<String, String> extensions = new HashMap<>();
        callManager.getActiveCalls().forEach(call ->
                extensions.put(call.getExtension(), call.getStatus())
        );
        log.debug("📞 Retornando {} extensiones activas", extensions.size());
        return ResponseEntity.ok(extensions);
    }

    @PostMapping("/calls/{callId}/hold")
    public ResponseEntity<Map<String, String>> holdCall(@PathVariable String callId) {
        callManager.updateCallStatus(callId, "HOLD");
        return ResponseEntity.ok(Map.of("status", "HOLD", "callId", callId));
    }

    @PostMapping("/calls/{callId}/resume")
    public ResponseEntity<Map<String, String>> resumeCall(@PathVariable String callId) {
        callManager.updateCallStatus(callId, "RESUME");
        return ResponseEntity.ok(Map.of("status", "RESUME", "callId", callId));
    }
}
