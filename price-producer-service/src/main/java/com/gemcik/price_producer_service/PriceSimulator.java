package com.gemcik.price_producer_service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class PriceSimulator {

    private final KafkaTemplate<String, StockUpdate> kafkaTemplate;
    private final WebClient webClient;

    @Value("${finnhub.api.key}")
    private String apiKey;

    public PriceSimulator(KafkaTemplate<String, StockUpdate> kafkaTemplate, WebClient finnhubWebClient) {
        this.kafkaTemplate = kafkaTemplate;
        this.webClient = finnhubWebClient;
    }

    // Run every 10 seconds to avoid rate limits
    @Scheduled(fixedRate = 5000)
    public void fetchAndSendPrices() {
        // Fetch prices for BTC and ETH
        fetchPrice("BINANCE:BTCUSDT", "BTC");
        fetchPrice("BINANCE:ETHUSDT", "ETH");
    }

    private void fetchPrice(String finnhubSymbol, String displaySymbol) {
        webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/quote")
                .queryParam("symbol", finnhubSymbol)
                .queryParam("token", apiKey)
                .build())
            .retrieve()
            .bodyToMono(FinnhubQuote.class)
            .doOnError(error -> System.err.println("Error fetching price for " + finnhubSymbol + ": " + error.getMessage()))
            .subscribe(quote -> {
                // Once we get the quote, create our StockUpdate
                StockUpdate update = new StockUpdate(
                    displaySymbol,
                    quote.currentPrice(),
                    System.currentTimeMillis()
                );

                // Send it to Kafka
                kafkaTemplate.send("stock-prices", update.symbol(), update);
                System.out.println("Sent: " + update);
            });
    }
}