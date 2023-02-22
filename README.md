# Event-Driven microservices demo project
A containerized event-driven microservices demo project developed using `Spring Boot`, `Kafka`, `Elasticsearch` and `Docker`. The application will 
read Tweets from Tweeter API and create new Kafka topics using Tweet data.

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

***Checking a particular topic in Kafkacat***<br/>
The following command will check whether a particular topic has been created (ex: twitter-topic).
```
docker run -it --network=host confluentinc/cp-kafkacat kafkacat -C -b  localhost:19092  -t twitter-topic
```

**Generate Avro schemas via IntelliJ**<br/>
Assuming that the project is opened in IntelliJ (an alternative to running the command `mvn clean install` when Maven is installed on the machine).
* Select the Maven tab

* Select the option: Execute Maven Goal

* Select the module (kafka-model)

* Type and enter "mvn clean install"

<p><img src="https://i.imgur.com/K0Xid2W.png"/></p>

## Docker

**Docker building the microservices** <br/>
The microservices of this application can docker build using the command `mvn install -DskipTests` via intelliJ or commandline. It should be 
executed under `microservices-demo` as shown below.

<p><img src="https://i.imgur.com/JaXcuop.png"/></p>

**Starting the services** <br/>
After building the microservices, the services can be started by running the following
 command inside `docker-compose` directory.
```
docker-compose up
```

Once the services are up and running, we should be able to see them using the command:
```
docker ps -a
```
<p><img src="https://i.imgur.com/3BjxzDA.png"/></p>

## Running services without Docker (except Kafka services)

+ **Step 1:** <br/>
Run Kafka services in Docker using the following command inside `docker-compose` directory.
    ```
    docker-compose -f common.yml -f kafka_cluster.yml up
    ```

+ **Step 2:** <br/>
Run `config-server` and `twitter-to-kafka-service` from IntelliJ in order.
+<p><img src="https://i.imgur.com/OB4jYch.png" /></p>

    Once the application is running successfully, it should display the `twitter-to-kafka-service` 
    connects with `config-server` via http://localhost:8888 to fetch all the configs required.
    <p><img src="https://i.imgur.com/nw2eT2Y.png" /></p>
    





