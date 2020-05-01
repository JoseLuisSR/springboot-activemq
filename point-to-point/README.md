# Spring Boot & ActiveMQ

This is a guide to build spring boot application with embedded ActiveMQ customer broker.

It is a web application that use different frameworks and dependencies to enable and set up ActiveMQ, expose a Rest/Json web service to put customer on queue and create a listener to get messages.

Below you can see all the frameworks and dependencies necessary to build it.

## ActiveMQ

Version 5.15.12

[ActiveMQ](https://activemq.apache.org/) is a open source queue server with customer broker capabilities like send, store and get messages
on queue or topics. ActiveMQ support different protocols to communicate between clients and ActiveMQ server.

This project use a single queue to put and get customer, all customer are stored on memory, you can configure
to [persistence](https://activemq.apache.org/persistence) the customer also . 


## JMX (Java Management Extension)

[JMX](https://openjdk.java.net/groups/jmx/) is a standard API for management and monitoring Java applications at runtime. JMX allow integration between Java application 
and JMX console/client like [JConsole](https://docs.oracle.com/javase/7/docs/technotes/guides/management/jconsole.html) to monitoring and 
provide information about the performance and resource consumption of application running on Java platform.

ActiveMQ is using JMX to manage resources through [JMX Beans](https://activemq.apache.org/jmx) to control the behaviour of the Broker.

## Jolokia

[Jolokia](https://jolokia.org/index.html) is an agent installed on server side to allow integration with JMX through API HTTP/Json. Application running on Java platforms and using JMX can use Jolokia 
to manage and monitoring the resources over HTTP.

ActiveMQ provide RESTFul management API for broker through Jolokia and JMX to access queue, topics, get broker metrics and execute manage operations all over HTTP with RESTful API.

Also ActiveMQ expose [RESTful](https://activemq.apache.org/rest) API to publish and consuming customer over HTTP GET & POST methods. These API are not enable with ActiveMQ embedded on Spring Boot.

## Hawtio

[Hawtio](https://hawt.io/) is a web application that shows the metrics, monitoring and manage resource of Java applications that are using Jolokia to expose JMX API over HTTP. We can deploy Hawtio console
on [spring boot](https://hawt.io/docs/get-started/#running-a-spring-boot-app) application to use the interface (web page, flows) to shows operation information of the application. 

ActiveMQ embedded on Spring Boot doesn't have the default [web console](https://activemq.apache.org/web-console) for that reason is necessary use [Hawtio ActiveMQ plugin](https://hawt.io/docs/plugins/) 
to connect ActiveMQ and check the status of the queue and other ActiveMQ components.

# Steps

Below are the steps to set up and deploy spring boot application with ActiveMQ embedded.

## Configuration

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

## Properties

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

## Sender

Define @Service class with the logic to put customer on queue. Use JmsTemplate class (simplifies synchronous JMS access code) to 
access ActiveMQ queue and put customer. Also is necessary define a @RestController to expose Rest/Json end-point to publish customer through @Service class.

You can find the classes definitions in this project.

## Receiver

The @EnableJms annotation enable the receiver class with @JmsListener annotation to create listener container to specific ActiveMQ queue and access to it 
through JMSListenerContainerFactory. Spring inject JMS objects at runtime to connect ActiveMQ, access to queue and get the messages.

You can find the classes definitions in this project



## Entity

You can send and receive objects through ActiveMQ. This project is using Jackson library to convert objects(messages) to and from Json.


# Build

activemq project is using Gradle to resolve dependencies from repositories, also to build jar file, just type in your console


    gradle build 

    
You can find the jar file in the build/libs directory with the name `activemq-x.y.z.jar`

Gradle has the option to build without having to install the Gradle runtime through [Gradle Wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html).

# Run

Just run jar file with the below commando in the console.

    java -jar activemq-x.y.z.jar

# Test

This repository has JMeter test plan configure with HTTP Request to send customer through 
Path variable or Body data. The end-point is:

    http://localhost:8080/sender/
    
To Access Hawtio console and see the queue size and other metrics then just type the URL 

    http://localhost:8080/actuator/hawtio/