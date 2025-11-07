package com.gemcik.price_producer_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class PriceProducerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PriceProducerServiceApplication.class, args);
	}

}
