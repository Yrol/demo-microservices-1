package com.microservices.demo.twitter.to.kafka.service.listener;

import com.microservices.demo.config.KafkaConfigData;
import com.microservices.demo.kafka.avro.model.TwitterAvroModel;
import com.microservices.demo.kafka.producer.config.service.KafkaProducer;
import com.microservices.demo.twitter.to.kafka.service.transformer.TwitterStatusToAvroTransformer;
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

    private final KafkaConfigData kafkaConfigData;

    private final KafkaProducer<Long, TwitterAvroModel> kafkaProducer;

    private final TwitterStatusToAvroTransformer twitterStatusToAvroTransformer;

    public  TwitterKafkaStatusListener(KafkaConfigData configData, KafkaProducer producer, TwitterStatusToAvroTransformer avroTransformer){
        this.kafkaConfigData = configData;
        this.kafkaProducer = producer;
        this.twitterStatusToAvroTransformer = avroTransformer;
    }

    @Override
    public void onStatus(Status status) {
        LOG.info("Twitter status with text {} sending to kafka topic {}", status.getText(), kafkaConfigData.getTopicName());

        /*
        * Sending twitter data to create topics.
        * Using user ID (getUserId) as key, which means partitioning the data using the user ID, and the tweets belongs to a user will be added to the same partition.
        * */
        TwitterAvroModel twitterAvroModel = twitterStatusToAvroTransformer.getTwitterAvroModelFromStatus(status);
        kafkaProducer.send(kafkaConfigData.getTopicName(), twitterAvroModel.getUserId(), twitterAvroModel);
    }
}
