package com.aihc.transithub.travel.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * WebSocket handler for managing custom events.
 * Broadcasts events to all connected clients.
 *
 * @author Alvaro Huanca
 */
@Component
public class CustomWebSocketHandler extends TextWebSocketHandler {

    private final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        System.out.println("Custom WebSocket client connected: " + session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        System.out.println("Custom WebSocket client disconnected: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            for (WebSocketSession socketSession : sessions) {
                if (socketSession.isOpen() && !socketSession.getId().equals(session.getId())) {
                    try {
                        socketSession.sendMessage(message);
                    } catch (Exception e) {
                        System.err.println("Error sending WebSocket message to session " + socketSession.getId() + ": " + e.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error serializing WebSocket message: " + e.getMessage());
        }
    }
}

