package com.iadv.cti.avaya.service;

import com.iadv.cti.avaya.model.Call;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class CallStateManager {
    private final ConcurrentHashMap<String, Call> activeCalls = new ConcurrentHashMap<>();

    public void addCall(Call call) {
        call.setLastUpdate(LocalDateTime.now());
        Call existing = activeCalls.putIfAbsent(call.getCallId(), call);
        if (existing != null) {
            log.warn("⚠️ Llamada duplicada: {}", call.getCallId());
            updateCall(call);
        } else {
            log.info("➕ Llamada agregada: {}", call.getCallId());
        }
    }

    public void updateCall(Call call) {
        activeCalls.computeIfPresent(call.getCallId(), (k, v) -> {
            v.setStatus(call.getStatus());
            v.setLastUpdate(LocalDateTime.now());
            return v;
        });
        log.info("🔄 Llamada actualizada: {} -> {}", call.getCallId(), call.getStatus());
    }

    public void updateCallStatus(String callId, String status) {
        activeCalls.computeIfPresent(callId, (k, v) -> {
            v.setStatus(status);
            v.setLastUpdate(LocalDateTime.now());
            return v;
        });
        log.info("🔄 Llamada {} cambiada a estado: {}", callId, status);
    }

    public void removeCall(String callId) {
        Call removed = activeCalls.remove(callId);
        if (removed != null) {
            log.info("➖ Llamada removida: {} (estado final: {})", callId, removed.getStatus());
        }
    }

    public List<Call> getActiveCalls() {
        return new ArrayList<>(activeCalls.values());
    }

    public Call getCall(String callId) {
        return activeCalls.get(callId);
    }

    public int getActiveCallCount() {
        return activeCalls.size();
    }

    public void clearAll() {
        activeCalls.clear();
        log.info("🧹 Todas las llamadas limpiadas");
    }
}
