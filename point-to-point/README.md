# Point-to-Point

This message style is very helpful to process high volume of transactions in asynchronous way with 
many workers ready to receive and process the transactions. Each message is unique and should 
process by one worker to avoid reprocessing.

The transactions or request are package in a message, then send to ActiveMQ to put in a queue 
and then dispatch to one consumer. There are two dispatch policies:

* Round Robin (Default): Load balance the messages between consumer following Round Robin 
scheduling algorithm.

* Strict Order: The message are dispatch in the same order received (FIFO) to only one 
consumer.

## Architecture

![Screenshot](https://github.com/JoseLuisSR/springboot-activemq/blob/master/doc/img/Point-to-Point-FV.png?raw=true)

Producers connect to ActiveMQ and put messages on queue. These are using JMS to do it, 
ActiveMQ supports [Support Multi-protocols](https://activemq.apache.org/cross-language-clients) also. Producer 
don't worry about the communication and availability with consumers to avoid the coupling 
between those.

ActiveMQ is the message broker with the capabilities to receive, store and dispatch messages.
ActiveMQ attend producers and consumers to connect, put and get messages on queue, apply 
different security, availability and performance policies to achieve Non functional requirements.

Consumers connect to ActiveMQ and receive messages from queue. These are using JMS to do it, 
ActiveMQ supports [Support Multi-protocols](https://activemq.apache.org/cross-language-clients) also. It has the logic 
to process the messages and execute business logic, also can produce response message and 
put on ActiveMQ queue to reply origin producer.


## Project

This is a set of spring boot projects with different capabilities:

* Producer: Set up embedded ActiveMQ on Spring Boot, exposes RestFUL end-points to put 
messages (JMS and custom objects) on queue through JMS API.

* Consumer: Spring boot application that connect to ActiveMQ and receive the messages 
through queues listeners using JMS API.

One application can be either producer and consumer at the same time and use embedded 
ActiveMQ also.

Below are the steps to set up and deploy spring boot application with ActiveMQ embedded.

### Configuration

Below are the dependencies necessary to use Spring Boot and ActiveMQ. We are using Gradle to manage the dependencies, you can use Maven also.

	implementation 'org.springframework.boot:spring-boot-starter-activemq'
	compile 'org.apache.activemq:activemq-broker'
	compile 'com.fasterxml.jackson.core:jackson-databind'
	compile 'org.jolokia:jolokia-core'
	compile 'io.hawt:hawtio-springboot:2.9.1'

	compileOnly 'org.projectlombok:lombok:1.18.12'
	annotationProcessor 'org.projectlombok:lombok:1.18.12'
	
Lombok is to build getter and setter methods automatically. It is not a dependency for Spring Boot & ActiveMQ.

Spring Boot can automatically configure a ConnectionFactory when it detects ActiveMQ is available on the class-path.
We are using configuration class to declare @Beans methods to get the DefaultJmsListenerContainerFactory and set
the customer convert to and from JSON. The annotation @EnableJMS allow define classes with method to recieve customer with 
@JmsListener annotation.

### Properties

When [ActiveMQ properties](https://docs.spring.io/spring-boot/docs/current/reference/html/appendix-application-properties.html#integration-properties) are present an embedded ActiveMQ broker is started and configured automatically, you can configure connection to remote
broker adding url also.

    # Embedded ActiveMQ Configuration Example
    
    spring.activemq.broker-url=vm://embedded?broker.persistent=false,useShutdownHook=false
    spring.activemq.in-memory=true
    spring.activemq.close-timeout=15
    spring.activemq.password=admin
    spring.activemq.user=admin

Remember that we need use Jolokia and Hawtio to integrate with JMX end-point and get access to ActiveMQ resources and metrics
through Hawtio web console. Add the below properties also:

    # Hawtio client
    
    management.endpoints.web.exposure.include=hawtio,jolokia
    hawtio.authenticationEnabled=false

### Sender

Define @Service class with the logic to put customer on queue. Use JmsTemplate class (simplifies synchronous JMS access code) to 
access ActiveMQ queue and put customer. Also is necessary define a @RestController to expose Rest/Json end-point to publish customer through @Service class.

You can find the classes definitions in this project.

### Receiver

The @EnableJms annotation enable the receiver class with @JmsListener annotation to create listener container to specific ActiveMQ queue and access to it 
through JMSListenerContainerFactory. Spring inject JMS objects at runtime to connect ActiveMQ, access to queue and get the messages.

You can find the classes definitions in this project

### Entity

You can send and receive objects through ActiveMQ. This project is using Jackson library to convert objects(messages) to and from Json.


## Build

activemq project is using Gradle to resolve dependencies from repositories, also to build jar file, just type in your console


    gradle build 

    
You can find the jar file in the build/libs directory with the name `activemq-x.y.z.jar`

Gradle has the option to build without having to install the Gradle runtime through [Gradle Wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html).

## Run

Just run jar file with the below commando in the console.

    java -jar activemq-x.y.z.jar

## Test

This repository has JMeter test plan configure with HTTP Request to send customer through 
Path variable or Body data. The end-point is:

    http://localhost:8080/sender/
    
To Access Hawtio console and see the queue size and other metrics then just type the URL 

    http://localhost:8080/actuator/hawtio/