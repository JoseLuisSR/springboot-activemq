# Spring Boot & ActiveMQ

This is a guide to build spring boot application with embedded ActiveMQ message broker.

It is a web application that use different frameworks and dependencies to enable and set up ActiveMQ, expose a Rest/Json web service to put message on queue and create a listener to get messages.

Below you can see all the frameworks and dependencies necessary to build it.

## ActiveMQ

Version 5.15.12

[ActiveMQ](https://activemq.apache.org/) is a open source queue server with message broker capabilities like send, store and get messages
on queue or topics. ActiveMQ support different protocols to communicate between clients and ActiveMQ server.

This project use a single queue to put and get message, all message are stored on memory, you can configure
to [persistence](https://activemq.apache.org/persistence) the message also . 


## JXM (Java Management Extension)

[JMX](https://openjdk.java.net/groups/jmx/) is a standard API for management and monitoring Java applications at runtime. JMX allow integration between Java application 
and JMX console/client like [JConsole](https://docs.oracle.com/javase/7/docs/technotes/guides/management/jconsole.html) to monitoring and 
provide information about the performance and resource consumption of application running on Java platform.

ActiveMQ is using JMX to manage resources through [JMX Beans](https://activemq.apache.org/jmx) to control the behaviour of the Broker.

## Jolokia

[Jolokia](https://jolokia.org/index.html) is an agent installed on server side to allow integration with JMX through API HTTP/Json. Application running on Java platforms and using JMX can use Jolokia 
to manage and monitoring the resources over HTTP.

ActiveMQ provide RESTFul management API for broker through Jolokia and JMX to access queue, topics, get broker metrics and execute manage operations all over HTTP with RESTful API.

Also ActiveMQ expose [RESTful](https://activemq.apache.org/rest) API to publish and consuming message over HTTP GET & POST methods. These API are not enable with ActiveMQ embedded on Spring Boot.

## Hawtio

[Hawtio](https://hawt.io/) is a web application that shows the metrics, monitoring and manage resource of Java applications that are using Jolokia to expose JMX API over HTTP. We can deploy Hawtio console
on [spring boot](https://hawt.io/docs/get-started/#running-a-spring-boot-app) application to use the interface (web page, flows) to shows operation information of the application. 

ActiveMQ embedded on Spring Boot doesn't have the default [web console](https://activemq.apache.org/web-console) for that reason is necessary use [Hawtio ActiveMQ plugin](https://hawt.io/docs/plugins/) 
to connect ActiveMQ and check the status of the queue and other ActiveMQ components.

## Steps

Below are the steps to set up and deploy spring boot application with ActiveMQ embedded.