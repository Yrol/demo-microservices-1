package com.microservices.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/*
* Using lombok for setters and getter
* Reading config data from application.yml file. Reading from "twitter-to-kafka-service" section of the yml file
* Each variable will be mapped to the property in the application.yml file ex: twitter-keywords -> twitterKeywords
* */
@Data
@Configuration
@ConfigurationProperties(prefix = "twitter-to-kafka-service")
public class TwitterToKafkaServiceConfigData {

    private List<String> twitterKeywords;
    private String welcomeMessage;
    private String TwitterV2BaseUrl;
    private String TwitterV2RulesBaseUrl;
    private String TwitterV2BearerToken;

    // Mock data configs
    private boolean enableMockTweets;
    private int mockMinTweetLength;
    private int mockMaxTweetLength;
    private long mockMsSleep;
}
