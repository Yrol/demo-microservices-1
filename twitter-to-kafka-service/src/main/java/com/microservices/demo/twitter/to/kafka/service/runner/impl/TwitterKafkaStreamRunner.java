package com.microservices.demo.twitter.to.kafka.service.runner.impl;

import com.microservices.demo.twitter.to.kafka.service.config.TwitterToKafkaServiceConfigData;
import com.microservices.demo.twitter.to.kafka.service.listener.TwitterKafkaStatusListener;
import com.microservices.demo.twitter.to.kafka.service.runner.StreamRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import twitter4j.FilterQuery;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

import javax.annotation.PreDestroy;
import java.util.Arrays;
import java.util.List;

/**
 * Tweet loading method 1 relying on old implementation (Not V2)
 * This Spring bean will load at runtime via ConditionalOnExpression based on conditions set in  src/main/resources/application.yml
 * */


//@ConditionalOnProperty(name = "twitter-to-kafka-service.enable-v2-tweets", havingValue = "false")
@ConditionalOnExpression("${twitter-to-kafka-service.enable-v1-tweets} && not ${twitter-to-kafka-service.enable-v2-tweets} && not ${twitter-to-kafka-service.enable-mock-tweets}")
@Component
public class TwitterKafkaStreamRunner implements StreamRunner {

    private static final Logger LOG = LoggerFactory.getLogger(TwitterKafkaStreamRunner.class);

    private final TwitterToKafkaServiceConfigData twitterToKafkaServiceConfigData;
    private final TwitterKafkaStatusListener twitterKafkaStatusListener;

    private TwitterStream twitterStream;

    // Constructor DI
    public TwitterKafkaStreamRunner(TwitterToKafkaServiceConfigData configData, TwitterKafkaStatusListener  statusListener) {
        twitterToKafkaServiceConfigData = configData;
        twitterKafkaStatusListener = statusListener;
    }

    @Override
    public void start() throws TwitterException {
        twitterStream = new TwitterStreamFactory().getInstance();
        twitterStream.addListener(twitterKafkaStatusListener);
        addFilter();
    }

    // Method to execute when closing the twitter stream (using PreDestroy annotation).
    @PreDestroy
    public void shutdown() {
        if(twitterStream != null) {
            LOG.info("Closing the twitter stream");
            twitterStream.shutdown();
        }
    }

    private void addFilter() {
        List<String> keywordList =  twitterToKafkaServiceConfigData.getTwitterKeywords();
        String[] keywords = keywordList.toArray(new String[keywordList.size()]); // converting List to Array
        FilterQuery filterQuery = new FilterQuery(keywords);
        twitterStream.filter(filterQuery);
        LOG.info("Started filtering twitter stream for keywords {}", Arrays.toString(keywords));
    }
}
