package com.microservices.demo.twitter.to.kafka.service;

import com.microservices.demo.config.TwitterToKafkaServiceConfigData;
import com.microservices.demo.twitter.to.kafka.service.init.StreamInitializer;
import com.microservices.demo.twitter.to.kafka.service.runner.StreamRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;


/*
* Using the CommandLineRunner to run specific code only once. Same can be achieved using the ApplicationListener
* */
@SpringBootApplication

//ComponentScan will add the base package "com.microservices.demo" of other modules such as app-config-data, common-config, kafka (and its sub-modules) & etc
@ComponentScan(basePackages = "com.microservices.demo")
public class TwitterKafkaServiceApplication implements CommandLineRunner {

    // Logging data of this class using slf4j logger
    public static final Logger LOG = LoggerFactory.getLogger(TwitterKafkaServiceApplication.class);
    private final TwitterToKafkaServiceConfigData twitterToKafkaServiceConfigData;
    private final StreamRunner streamRunner;
    private final StreamInitializer streamInitializer;

    // Using DI with constructor injection to inject the TwitterToKafkaServiceConfigData class. Can also be done using field injection @Autowired
    public TwitterKafkaServiceApplication(TwitterToKafkaServiceConfigData configData, StreamRunner runner, StreamInitializer initializer) {
        twitterToKafkaServiceConfigData = configData;
        streamRunner = runner;
        this.streamInitializer = initializer;
    }

    public static void main(String[] args) {
        SpringApplication.run(TwitterKafkaServiceApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        LOG.info("App started");
//        LOG.info(Arrays.toString(twitterToKafkaServiceConfigData.getTwitterKeywords().toArray())); // Will print the values of "twitter-keywords"
//        LOG.info(twitterToKafkaServiceConfigData.getWelcomeMessage());

        // Check if the schema registry is running and Kafka topics has been created - before starting the twitter stream below.
        streamInitializer.init();

        // Starting the twitter stream
        streamRunner.start();
    }
}
