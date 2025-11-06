package com.gemcik.price_producer_service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    // This string MUST exactly match the property name
    @Value("${finnhub.api.base-url}")
    private String baseUrl;

    @Bean
    public WebClient finnhubWebClient() {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }
}