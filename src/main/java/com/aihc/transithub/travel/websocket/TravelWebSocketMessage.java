package com.aihc.transithub.travel.websocket;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * WebSocket message DTO for broadcasting travel entity events.
 * Contains event type and the entity data.
 *
 * @author Alvaro Huanca
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TravelWebSocketMessage {

    @JsonProperty("event_type")
    private WebSocketEventType eventType;

    @JsonProperty("entity_type")
    private WebSocketEntityType entityType;

//    @JsonProperty("data")
//    private Object data;

    @JsonProperty("timestamp")
    private Long timestamp;
}

