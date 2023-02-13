package com.microservices.demo.kafka.admin.exception;

/**
 * Custom exception class for Kafka exceptions
 * */

public class KafkaClientException extends RuntimeException {

    public KafkaClientException() {}

    public KafkaClientException(String message) {
        super(message);
    }

    public KafkaClientException(String message, Throwable cause) {
        super(message,cause);
    }
}
