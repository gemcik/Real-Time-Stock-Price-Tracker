package com.gemcik.price_producer_service;

public record StockUpdate(String symbol, double price, long timestamp) {}