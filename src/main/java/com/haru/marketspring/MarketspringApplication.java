package com.haru.marketspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MarketspringApplication {

    public static void main(String[] args) {
        SpringApplication.run(MarketspringApplication.class, args);
    }

}