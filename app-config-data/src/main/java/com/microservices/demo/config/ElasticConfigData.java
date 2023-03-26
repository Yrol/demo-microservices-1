package com.microservices.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "elastic-config") // elastic-config defined in config-client-kafka_to_elastic.yml
public class ElasticConfigData {

    private String indexName;
    private String connectionUrl;
    private Integer connectionTimeoutMs;
    private Integer socketTimeoutMs;
}
