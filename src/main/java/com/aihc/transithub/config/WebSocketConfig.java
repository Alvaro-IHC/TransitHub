package com.aihc.transithub.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import com.aihc.transithub.travel.websocket.TravelWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Configuration class for WebSocket support in the application.
 * Enables WebSocket endpoints for real-time communication.
 * Supports multiple handlers for different purposes:
 * - /ws/travel: Travel events (tickets, parcels, trips)
 * - /ws/notifications: General notifications and alerts
 *
 * @author Alvaro Huanca
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private TravelWebSocketHandler travelWebSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // Travel events endpoint
        registry.addHandler(travelWebSocketHandler, "/ws/travel")
                .setAllowedOrigins("*");

        // Notifications endpoint
        // registry.addHandler(notificationWebSocketHandler, "/ws/notifications")
        //        .setAllowedOrigins("*");
    }
}



