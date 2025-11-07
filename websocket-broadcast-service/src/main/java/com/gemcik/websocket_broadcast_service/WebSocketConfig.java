package com.gemcik.websocket_broadcast_service;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket 
public class WebSocketConfig implements WebSocketConfigurer {

    private final PriceBroadcastHandler priceBroadcastHandler; 

    public WebSocketConfig(PriceBroadcastHandler priceBroadcastHandler) { 
        this.priceBroadcastHandler = priceBroadcastHandler; 
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(priceBroadcastHandler, "/price-stream") 
                .setAllowedOrigins("*"); 
    }
}