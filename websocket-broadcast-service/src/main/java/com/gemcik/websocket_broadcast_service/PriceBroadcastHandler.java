package com.gemcik.websocket_broadcast_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class PriceBroadcastHandler extends TextWebSocketHandler { 

    private final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>(); 
    private final ObjectMapper objectMapper = new ObjectMapper(); 

    @Override
    public void afterConnectionEstablished(WebSocketSession session) { 
        sessions.add(session);
        System.out.println("New WebSocket connection: " + session.getId()); 
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) { 
        sessions.remove(session); 
        System.out.println("WebSocket connection closed: " + session.getId()); 
    }

    public void broadcast(StockUpdate stockUpdate) {
        try {
            String messagePayload = objectMapper.writeValueAsString(stockUpdate); 
            TextMessage message = new TextMessage(messagePayload);

            for (WebSocketSession session : sessions) { 
                if (session.isOpen()) { 
                    session.sendMessage(message); 
                }
            }
        } catch (IOException e) {
            System.err.println("Error broadcasting message: " + e.getMessage()); 
        }
    }
}