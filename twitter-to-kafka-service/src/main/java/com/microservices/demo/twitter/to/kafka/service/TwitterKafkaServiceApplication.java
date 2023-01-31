package com.microservices.demo.twitter.to.kafka.service;

import com.microservices.demo.twitter.to.kafka.service.config.TwitterToKafkaServiceConfigData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;


/*
* Using the CommandLineRunner to run specific code only once. Same can be achieved using the ApplicationListener
* */
@SpringBootApplication
public class TwitterKafkaServiceApplication implements CommandLineRunner {

    // Logging data of this class using slf4j logger
    public static final Logger LOG = LoggerFactory.getLogger(TwitterKafkaServiceApplication.class);
    private final TwitterToKafkaServiceConfigData twitterToKafkaServiceConfigData;

    // Using DI with constructor injection to inject the TwitterToKafkaServiceConfigData class. Can also be done using field injection @Autowired
    public TwitterKafkaServiceApplication(TwitterToKafkaServiceConfigData configData) {
        twitterToKafkaServiceConfigData = configData;
    }

    public static void main(String[] args) {
        SpringApplication.run(TwitterKafkaServiceApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        LOG.info("App started");
        LOG.info(Arrays.toString(twitterToKafkaServiceConfigData.getTwitterKeywords().toArray())); // Will print the values of "twitter-keywords"
        LOG.info(twitterToKafkaServiceConfigData.getWelcomeMessage());
    }
}
