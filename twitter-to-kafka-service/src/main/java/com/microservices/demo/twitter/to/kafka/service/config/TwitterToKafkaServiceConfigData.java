package com.microservices.demo.twitter.to.kafka.service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/*
* Using lombok for setters and getter
* Reading string data from application.yml file
* */
@Data
@Configuration
@ConfigurationProperties(prefix = "twitter-to-kafka-service")
public class TwitterToKafkaServiceConfigData {

    // This will get populated from the value of "twitter-keywords" in src/main/resources/application.yml
    private List<String> twitterKeywords;
    private String welcomeMessage;
}
