package com.gemcik.price_producer_service;

import com.fasterxml.jackson.annotation.JsonProperty;

// "c" = current price, "pc" = previous close
public record FinnhubQuote(
    @JsonProperty("c") double currentPrice,
    @JsonProperty("pc") double previousClose
) {}