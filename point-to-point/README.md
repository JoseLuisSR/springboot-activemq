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
ActiveMQ [Support Multi-protocols](https://activemq.apache.org/cross-language-clients) also. Producer 
don't worry about the communication and availability with consumers to avoid the coupling 
between those.

ActiveMQ is the message broker with the capabilities to receive, store and dispatch messages.
ActiveMQ attend producers and consumers to connect, put and get messages on queue, apply 
different security, availability and performance policies to achieve Non functional requirements.

Consumers connect to ActiveMQ and receive messages from queue. These are using JMS to do it, 
ActiveMQ [Support Multi-protocols](https://activemq.apache.org/cross-language-clients) also. It has the logic 
to process the messages and execute business logic, also can produce response message and 
put on ActiveMQ queue to reply origin producer.


## Projects

In each project you can find more detail about set up and use ActiveMQ from spring boot.

* [Producer](https://github.com/JoseLuisSR/springboot-activemq/tree/master/point-to-point/producer): Set up embedded ActiveMQ on Spring Boot, exposes RestFUL end-points to put 
messages (JMS and custom objects) on queue through JMS API.

* [Consumer](https://github.com/JoseLuisSR/springboot-activemq/tree/master/point-to-point/consumer): Spring boot application that connect to ActiveMQ and receive the messages 
through queues listeners using JMS API.

One application can be either producer and consumer at the same time and use embedded 
ActiveMQ also.