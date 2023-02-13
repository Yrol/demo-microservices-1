package com.microservices.demo.kafka.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Customised webclient class that can be used for making web calls (REST, POST & etc)
 * */

@Configuration
public class WebClientConfig {

    // Load as a Bean so its available when the app is started and returns a Webclient object
    @Bean
    WebClient webClient() {
        return WebClient.builder().build();
    }
}
