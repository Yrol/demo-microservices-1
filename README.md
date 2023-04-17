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

## ElasticSearch
The Docker based ElasticSearch services are located in `/microservices-demo/docker-compose/elastic_cluster.yml`. The cluster consist of 3 nodes and the service can be accessed via `localhost:9200`.

To start ElasticSearch via Docker use the following command.
```
docker-compose -f common.yml -f elastic_cluster.yml up
```


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
  <p><img src="https://i.imgur.com/OB4jYch.png" /></p>

    Once the application is running successfully, it should display the `twitter-to-kafka-service` 
    connects with `config-server` via http://localhost:8888 to fetch all the configs required.
    <p><img src="https://i.imgur.com/nw2eT2Y.png" /></p>

## Passwords and environment variables
It is important add correct encrypted passwords to be able to fetch data from Twitter as well as connecting to Spring cloud 
as a prerequisite. The passwords need to be encrypted using Jasypt and for this purpose Playground module can be used.

### Jasypt decrypt password store location
The Jasypt decrypt password can be stored in `~/.zshrc` or `~/.bash_profile` as an environment variable. Once added please restart IntelliJ to reflect changes.

### Encrypt passwords using the Playground.
+ **Step 1:** Go to `src/main/java/jasypt/TestJasypt.java` and set the decrypt password from env variables: `standardPBEStringEncryptor.setPassword(System.getenv("TWITTER_TO_KAFKA_JASYPT"));`.

+ **Step 2:** Set the password /  string that needs to be encrypted in `standardPBEStringEncryptor.encrypt("string_to_be_encrypted");`.

+ **Step 3:** Run the project and copy the decrypted password from the console.

+ **Step 4:** Replace all the passwords using Jasypt encryption within the project. Ex: `ENC(abcd123)`.

+ **Step 5:** Update Jasypt configuration across the project with the new environment variable name added above to `~/.zshrc` or `~/.bash_profile`.
<p><img src="https://i.imgur.com/ZjVkdko.png"></p>

<p><img src="https://i.imgur.com/KLm52uQ.png"></p>

### Health checks
**Actuator health check** <br/>
To check the health of a service, for instance `config-server` service, use cURL when the services are up and running in the docker.
```
curl http://localhost:8888/actuator/health    
```

Where 8888 is the assigned port of the `config-server` service.









