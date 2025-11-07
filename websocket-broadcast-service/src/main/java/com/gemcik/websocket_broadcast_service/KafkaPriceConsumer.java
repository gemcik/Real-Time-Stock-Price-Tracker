package com.gemcik.websocket_broadcast_service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service 
public class KafkaPriceConsumer {

    private final PriceBroadcastHandler broadcastHandler; 

    public KafkaPriceConsumer(PriceBroadcastHandler broadcastHandler) { 
        this.broadcastHandler = broadcastHandler; 
    }

    @KafkaListener(topics = "stock-prices", groupId = "price-broadcaster")
    public void handlePriceUpdate(StockUpdate stockUpdate) {
        System.out.println("Received from Kafka: " + stockUpdate); 

        // Pass the message to the WebSocket handler
        broadcastHandler.broadcast(stockUpdate); 
    }
}