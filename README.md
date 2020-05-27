# Spring Boot & ActiveMQ

This is Spring Boot project with embedded ActiveMQ to exchange information between applications 
through message broker. It project has applications examples to send and receive messages using 
JMS API.

The goal is enable and set up ActiveMQ with Spring Boot, execute test from internal and external 
Java applications clients and identify weakness and strengths of embedded ActiveMQ.

# Spring Boot

Spring Boot is a project built on the top of the Spring framework. It provides a simpler and faster 
way to set up, configure, and run both simple and web-based applications.

Spring Boot has embedded Apache Tomcat Web Server & ActiveMQ message broker.

![Screenshot](https://github.com/JoseLuisSR/springboot-activemq/blob/master/doc/img/Spring-Boot-ActiveMQ-FV.png?raw=true)

# ActiveMQ

[ActiveMQ](https://activemq.apache.org/) is a Message Broker to exchange information between applications 
through messages, queues and topics. ActiveMQ is an asynchronous messaging system MOM 
(Message Oriented Middleware) that enable decoupling communication between heterogeneous 
applications that we call senders and consumers or publishers and subscribers.

ActiveMQ is open source written in Java and with JMS, Rest WebSockets and JMX interfaces. 
[Support Multi-protocols](https://activemq.apache.org/cross-language-clients) like AMQP, 
MQTT (IoT), STOMP, also has libraries for different programing languages like Java, C, C++, 
Phyton, .Net, Go, etc.

Embedded ActiveMQ on Spring Boot has the version 5.15.12.

# Messaging Styles

ActiveMQ is designed for high performance and high throughput messaging where the messages 
are sending to consumers as quickly as possible.

ActiveMQ supports two messaging styles of asynchronous messaging:

## [Point to Point](https://github.com/JoseLuisSR/springboot-activemq/tree/master/point-to-point)

Enable exchange information between producers and consumers through message and queue. 
Each message is receive by one single consumer and the messages can generate by multiple 
producers. ActiveMQ manage the connection from applications to the queue and deliver the 
messages following dispatch policies like Round Robin (by default) or strict order, 
you can choose one of these depending of your use case.

## [Publisher & Subscribe](https://github.com/JoseLuisSR/springboot-activemq/tree/master/publish-and-subscribe) 

It is a software design pattern where ActiveMQ message broker is the key piece to implement it.
Also used to exchange information between Publishers and Subscribers through messages and 
topics. Each message is send to all applications subscribe to the topic and the message can 
generate by multiple publishers.

# Projects

These are web applications using different frameworks and dependencies to enable and set up ActiveMQ, 
expose a Rest/Json web service to put messages on queue/topic and create a listener to get messages.

Below you can see all the frameworks and dependencies necessary to build it.

## JMX (Java Management Extension)

[JMX](https://openjdk.java.net/groups/jmx/) is a standard API for management and monitoring Java applications at runtime. JMX allow integration between Java application 
and JMX console/client like [JConsole](https://docs.oracle.com/javase/7/docs/technotes/guides/management/jconsole.html) to monitoring and 
provide information about the performance and resource consumption of application running on Java platform.

ActiveMQ is using JMX to manage resources through [JMX Beans](https://activemq.apache.org/jmx) to control the behaviour of the Broker.

## Jolokia

[Jolokia](https://jolokia.org/index.html) is an agent installed on server side to allow integration with JMX through API HTTP/JSON. Application running on Java platforms and using JMX can use Jolokia 
to manage and monitoring the resources over HTTP.

ActiveMQ provide RestFUL management API for broker through Jolokia and JMX to access queue, topics, get broker metrics and execute manage operations all over HTTP.

Also ActiveMQ expose [RestFUL](https://activemq.apache.org/rest) API to publish and consuming customer over HTTP GET & POST methods. These API are not enable with ActiveMQ embedded on Spring Boot.

## Hawtio

[Hawtio](https://hawt.io/) is a web application that shows metrics, monitoring and manage resource of Java applications that are using Jolokia to expose JMX API over HTTP. We can deploy Hawtio console
on [spring boot](https://hawt.io/docs/get-started/#running-a-spring-boot-app) application to use the interface (web page, flows) to shows operation information of the application. 

ActiveMQ embedded on Spring Boot doesn't have the default [web console](https://activemq.apache.org/web-console) for that reason is necessary use [Hawtio ActiveMQ plugin](https://hawt.io/docs/plugins/) 
to connect ActiveMQ and check the status of the queue/topics and other ActiveMQ components.

Below image is an example of the Hawtio web console integrated with embedded ActiveMQ Spring Boot.

![Screenshot](https://github.com/JoseLuisSR/springboot-activemq/blob/master/doc/img/Qhawtio-queues.png?raw=true)
