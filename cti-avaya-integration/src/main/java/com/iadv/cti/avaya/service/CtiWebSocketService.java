package com.iadv.cti.avaya.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iadv.cti.avaya.model.Agent;
import com.iadv.cti.avaya.model.Call;
import com.iadv.cti.avaya.model.CtiEvent;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.*;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@Slf4j
public class CtiWebSocketService {

    private final CallStateManager callManager;
    private final AgentStateManager agentManager;
    private final WebSocketClient webSocketClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${cti.websocket.url:ws://precook-overtone-syndrome.ngrok-free.dev}")
    private String wsUrl;

    @Value("${cti.websocket.reconnect-delay:5000}")
    private long reconnectDelay;

    private WebSocketConnectionManager connectionManager;
    private WebSocketSession session;
    private final AtomicBoolean connected = new AtomicBoolean(false);
    private final ScheduledExecutorService reconnectScheduler = Executors.newSingleThreadScheduledExecutor();

    public CtiWebSocketService(CallStateManager callManager,
                               AgentStateManager agentManager,
                               WebSocketClient webSocketClient) {
        this.callManager = callManager;
        this.agentManager = agentManager;
        this.webSocketClient = webSocketClient;
    }

    @PostConstruct
    public void init() {
        log.info("🚀 Inicializando servicio WebSocket CTI Avaya...");
        log.info("📡 Conectando a: {}", wsUrl);
        connect();
    }

    public void connect() {
        try {
            WebSocketHandler handler = new CtiWebSocketHandler();

            connectionManager = new WebSocketConnectionManager(
                    webSocketClient,
                    handler,
                    wsUrl
            );

            connectionManager.setAutoStartup(true);
            connectionManager.start();

            log.info("🔄 Conectando a WebSocket: {}", wsUrl);

        } catch (Exception e) {
            log.error("❌ Error conectando WebSocket: {}", e.getMessage());
            scheduleReconnect();
        }
    }

    private void scheduleReconnect() {
        reconnectScheduler.schedule(() -> {
            if (!connected.get()) {
                log.info("🔄 Intentando reconectar en {} segundos...", reconnectDelay / 1000);
                connect();
            }
        }, reconnectDelay, TimeUnit.MILLISECONDS);
    }

    @PreDestroy
    public void destroy() {
        try {
            if (connectionManager != null) {
                connectionManager.stop();
                log.info("🔌 WebSocket ConnectionManager detenido correctamente");
            }
            if (session != null && session.isOpen()) {
                session.close();
                log.info("🔌 WebSocket cerrado correctamente");
            }
        } catch (Exception e) {
            log.error("Error cerrando WebSocket: {}", e.getMessage());
        }
        reconnectScheduler.shutdown();
    }

    private class CtiWebSocketHandler extends AbstractWebSocketHandler {

        @Override
        public void afterConnectionEstablished(WebSocketSession session) {
            CtiWebSocketService.this.session = session;
            connected.set(true);
            log.info("✅ WebSocket conectado exitosamente");
            log.info("📡 Sesión ID: {}", session.getId());
        }

        @Override
        protected void handleTextMessage(WebSocketSession session, TextMessage message) {
            try {
                String payload = message.getPayload();
                log.info("📨 Evento recibido: {}", payload);

                CtiEvent event = objectMapper.readValue(payload, CtiEvent.class);
                processEvent(event);

            } catch (Exception e) {
                log.error("❌ Error procesando mensaje: {}", e.getMessage());
                log.error("Payload: {}", message.getPayload());
            }
        }

        @Override
        public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
            connected.set(false);
            log.warn("⚠️ WebSocket desconectado. Código: {}, Razón: {}",
                    status.getCode(), status.getReason());
            scheduleReconnect();
        }

        @Override
        public void handleTransportError(WebSocketSession session, Throwable exception) {
            log.error("❌ Error de transporte: {}", exception.getMessage());
            connected.set(false);
            scheduleReconnect();
        }
    }

    private void processEvent(CtiEvent event) {
        try {
            switch (event.getEventType()) {
                case "CALL_RECEIVED":
                    handleCallReceived(event);
                    break;

                case "CALL_ANSWERED":
                    handleCallAnswered(event);
                    break;

                case "CALL_HOLD":
                    handleCallHold(event);
                    break;

                case "CALL_RESUME":
                    handleCallResume(event);
                    break;

                case "CALL_TRANSFER":
                    handleCallTransfer(event);
                    break;

                case "CALL_ENDED":
                    handleCallEnded(event);
                    break;

                case "HEARTBEAT":
                    log.debug("💓 Heartbeat recibido");
                    break;

                default:
                    log.warn("⚠️ Evento desconocido: {}", event.getEventType());
            }
        } catch (Exception e) {
            log.error("❌ Error procesando evento {}: {}", event.getEventType(), e.getMessage());
        }
    }

    private void handleCallReceived(CtiEvent event) {
        Call call = new Call();
        call.setCallId(event.getCallId());
        call.setExtension(event.getExtension());
        call.setAgentId(event.getAgentId());
        call.setPhoneNumber(event.getPhoneNumber());
        call.setStatus("RECEIVED");
        call.setTimestamp(LocalDateTime.now());
        call.setLastUpdate(LocalDateTime.now());

        callManager.addCall(call);
        updateAgentStatus(event.getAgentId(), "BUSY", event.getCallId());

        log.info("📞 Llamada recibida: {} desde {}", event.getCallId(), event.getPhoneNumber());
    }

    private void handleCallAnswered(CtiEvent event) {
        callManager.updateCallStatus(event.getCallId(), "ANSWERED");
        log.info("✅ Llamada contestada: {}", event.getCallId());
    }

    private void handleCallHold(CtiEvent event) {
        callManager.updateCallStatus(event.getCallId(), "HOLD");
        log.info("⏸️ Llamada en espera: {}", event.getCallId());
    }

    private void handleCallResume(CtiEvent event) {
        callManager.updateCallStatus(event.getCallId(), "RESUME");
        log.info("▶️ Llamada reanudada: {}", event.getCallId());
    }

    private void handleCallTransfer(CtiEvent event) {
        callManager.updateCallStatus(event.getCallId(), "TRANSFER");
        log.info("🔄 Llamada transferida: {}", event.getCallId());
    }

    private void handleCallEnded(CtiEvent event) {
        callManager.removeCall(event.getCallId());
        updateAgentStatus(event.getAgentId(), "AVAILABLE", null);
        log.info("🔚 Llamada finalizada: {}", event.getCallId());
    }

    private void updateAgentStatus(String agentId, String status, String callId) {
        Agent agent = agentManager.getAgent(agentId);
        if (agent == null) {
            agent = new Agent();
            agent.setAgentId(agentId);
        }
        agent.setStatus(status);
        agent.setCurrentCallId(callId);
        agent.setLastUpdate(LocalDateTime.now());
        agentManager.updateAgent(agent);
    }
}