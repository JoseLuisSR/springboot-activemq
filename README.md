# Spring Boot & ActiveMQ

This is Spring Boot project with embedded ActiveMQ to exchange information between applications 
through message broker. It project has applications examples to send and receive messages using 
JMS API.

The goal is enable and set up ActiveMQ with Spring Boot, execute test from internal and external 
Java applications clients and identify weakness and strengths of embedded ActiveMQ.


# ActiveMQ

[ActiveMQ](https://activemq.apache.org/) is a Message Broker to exchange information between applications through send and 
receive messages on queue or topics. ActiveMQ is an asynchronous messaging system MOM 
(Message Oriented Middleware) that enable decoupling communication between heterogeneous 
applications that we call senders and consumers.

ActiveMQ is open source written in Java and with JMS, Rest WebSockets and JMX interfaces. 
[Support Multi-protocols](https://activemq.apache.org/cross-language-clients) like AMQP, 
MQTT (IoT), STOMP, also has libraries for different programing languages like Java, C, C++, 
Phyton, .Net, Go, etc.


## Messaging Styles

ActiveMQ is designed for high performance and high throughput messaging where there are lots of messages 
that need to be dispatched to consumers as quickly as possible.

ActiveMQ supports two messaging styles of asynchronous messaging:

## Point to Point

Enable exchange information between producers and consumers through message and queue. 
Each message is receive by one single consumer and the messages can generate by multiple 
producers. ActiveMQ manage the connection from applications to the queue and deliver the 
messages following dispatch policies like Round Robin (by default) or strict order, 
you can choose one of these depending to your use case.

## Publisher & Subscribe 

It is a software design pattern where ActiveMQ message broker is the key piece to implement it.
Also used to exchange information between Publishers and Subscribers through messages and 
topics. Each message is send to all applications subscribe to the topic and the message can 
generate by multiple publishers.
