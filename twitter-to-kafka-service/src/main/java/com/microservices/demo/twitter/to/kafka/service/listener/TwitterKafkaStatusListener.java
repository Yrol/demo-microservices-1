package com.microservices.demo.twitter.to.kafka.service.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import twitter4j.Status;
import twitter4j.StatusAdapter;

/**
 * Component class that is used for getting the Twitter status by extending the StatusAdapter of Twitter.
 * */

@Component
public class TwitterKafkaStatusListener extends StatusAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(TwitterKafkaStatusListener.class);

    @Override
    public void onStatus(Status status) {
        LOG.info("Twitter status with text {}", status.getText());
    }
}
