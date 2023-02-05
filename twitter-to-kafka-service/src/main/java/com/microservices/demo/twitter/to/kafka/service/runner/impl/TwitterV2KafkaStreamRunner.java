package com.microservices.demo.twitter.to.kafka.service.runner.impl;

import com.microservices.demo.twitter.to.kafka.service.config.TwitterToKafkaServiceConfigData;
import com.microservices.demo.twitter.to.kafka.service.runner.StreamRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of Twitter V2
 * This spring bean will load at runtime when "enable-v2-tweets" property is true in src/main/resources/application.yml
 * */

@Component
//@ConditionalOnProperty(name = "twitter-to-kafka-service.enable-v2-tweets", havingValue = "true", matchIfMissing = true)

//Using ConditionalOnExpression instead of ConditionalOnProperty for complex conditions. Loading real tweets to mock tweets based conditions set
@ConditionalOnExpression("${twitter-to-kafka-service.enable-v2-tweets} && not ${twitter-to-kafka-service.enable-mock-tweets}")
public class TwitterV2KafkaStreamRunner implements StreamRunner {

    private static final Logger LOG = LoggerFactory.getLogger(TwitterV2KafkaStreamRunner.class);

    private final TwitterToKafkaServiceConfigData twitterToKafkaServiceConfigData;

    private final TwitterV2StreamHelper twitterV2StreamHelper;

    public TwitterV2KafkaStreamRunner(TwitterToKafkaServiceConfigData configData, TwitterV2StreamHelper streamHelper) {
        this.twitterV2StreamHelper = streamHelper;
        this.twitterToKafkaServiceConfigData = configData;
    }

    @Override
    public void start() {
        String bearerToken = twitterToKafkaServiceConfigData.getTwitterV2BearerToken();

        if (null != bearerToken) {
            try {
                twitterV2StreamHelper.setupRules(bearerToken, getRules());
                twitterV2StreamHelper.connectStream(bearerToken);
            } catch (IOException | URISyntaxException e) {

            }
        } else {
            String error = "There was a problem getting the bearer token. Please make sure you set the TWITTER_BEARER_TOKEN environment variable.";
            LOG.error(error);
            throw new RuntimeException(error);
        }
    }

    private Map<String, String> getRules() {
        List<String> keywords = twitterToKafkaServiceConfigData.getTwitterKeywords();
        Map<String, String> rules = new HashMap<>();
        for (String keyword: keywords) {
            rules.put(keyword, "keyword: " + keyword);
        }
        LOG.info("Created filter for twitter stream for keywords: {}", keywords);
        return rules;
    }
}
