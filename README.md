# Event-Driven microservices demo project
A containerized event-driven microservices demo project developed using `Spring Boot`, `Kafka`, `Elasticsearch` and `Docker`.

## Kafka

**Kafka services overview** <br/>
The Docker based Kafka services are located in `/microservices-demo/docker-compose`, and these services consist of:
* Zookeeper -  1
* Brokers - 3
* schema-registry - 1

**Kafka module** <br/>
Located in `/Spring/microservices-demo/kafka`.
* kafka-admin - Create and verify Kafka topics programmatically.
* kafka-model - Create and hold Kafka objects in Avro format.
* kafka-producer - Use spring-kafka to write Kafka producer implementation.


**Running Kafka services** <br/>
To run these services, `cd` into `/microservices-demo/docker-compose` and execute the following command
```
docker-compose -f common.yml -f kafka_cluster.yml up 
```

**Using Kafkacat**<br/>
kafkacat is a commandline tool for interacting with Kafka brokers. It can be used to produce and consume messages, as well as query metadata.
For this we can use the docker based Kafkacat as below.

+ **Step 1:** Pulling the Kafkacat docker image.<br/>
  ```
  docker pull confluentinc/cp-kafkacat
  ```
  
+ **Step 2:** Running the Kafkacat by connecting to the brokers.<br/>
  Ports to be used for connecting to the brokers `19092`, `29092`, `39092` as specified in  `/microservices-demo/docker-compose/kafka_cluster.yml`.
  Use the following command (ex: port 19092):
  ```
  docker run -it --network=host confluentinc/cp-kafkacat kafkacat -L -b  localhost:19092  -L
  ```

