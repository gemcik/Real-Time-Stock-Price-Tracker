package com.gemcik.websocket_broadcast_service;

public record StockUpdate(String symbol, double price, long timestamp) {}
