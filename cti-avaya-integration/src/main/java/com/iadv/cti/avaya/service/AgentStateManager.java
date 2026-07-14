package com.iadv.cti.avaya.service;

import com.iadv.cti.avaya.model.Agent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class AgentStateManager {
    private final ConcurrentHashMap<String, Agent> agents = new ConcurrentHashMap<>();

    public void updateAgent(Agent agent) {
        agent.setLastUpdate(LocalDateTime.now());
        agents.put(agent.getAgentId(), agent);
        log.info("👤 Agente actualizado: {} -> {}", agent.getAgentId(), agent.getStatus());
    }

    public Agent getAgent(String agentId) {
        return agents.get(agentId);
    }

    public List<Agent> getAgents() {
        return new ArrayList<>(agents.values());
    }

    public void removeAgent(String agentId) {
        agents.remove(agentId);
        log.info("👤 Agente removido: {}", agentId);
    }

    public int getAgentCount() {
        return agents.size();
    }

    public void clearAll() {
        agents.clear();
        log.info("🧹 Todos los agentes limpiados");
    }
}
