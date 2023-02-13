package com.microservices.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration properties required for RetryConfig
 * */

@Data
@Configuration
@ConfigurationProperties(prefix = "retry-config") // fetching from
public class RetryConfigData {

    private long initialIntervalMs;
    private long maxIntervalMs;
    private Double multiplier;
    private int maxAttempts;
    private long sleepTimeMs;
}
