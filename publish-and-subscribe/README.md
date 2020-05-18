# Publisher & Subscriber

It is one of my favorites patterns, and is very helpful to communication between applications 
with low coupling. Publish & Subscribe is use in event driven architecture, communication 
between microservices and exchange information between bounded context with Domain Driven 
Design.

## Architecture

![Screenshot](https://github.com/JoseLuisSR/springboot-activemq/blob/master/doc/img/Publish-Subscribe-FV.png?raw=true)

Publisher send messages to store in ActiveMQ topic, it doesn't care where are the subscribers 
and which ones receive the messages, but needs to know where is the ActiveMQ server. 

ActiveMQ receive messages from publishers and store those in a topic, also record the 
subscribers interested in the topic and dispatch the messages to all subscribers. 

The subscriber listen the ActiveMQ topics and receive the messages, also needs to know where 
is ActiveMQ servers and connect to it. Subscribers can register to one or many topic they want.

## Projects

In each project you can find more detail about set up and use ActiveMQ from spring boot.

* [Publisher](https://github.com/JoseLuisSR/springboot-activemq/tree/master/publish-and-subscribe/publisher): Set up embedded ActiveMQ on Spring Boot, exposes RestFUL end-points to put 
messages (JMS and custom objects) on topic through JMS API.

* [Subscriber](https://github.com/JoseLuisSR/springboot-activemq/tree/master/publish-and-subscribe/subscriber): Spring boot application that connect to ActiveMQ and receive the messages 
through topic listeners using JMS API.

One application could be Publisher and Subscriber at the same time and use embedded 
ActiveMQ also.