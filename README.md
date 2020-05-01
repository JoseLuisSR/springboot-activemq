# Spring Boot & ActiveMQ

This is a guide to build spring boot application with embedded ActiveMQ customer broker.

# Messaging Styles

ActiveMQ message broker support two kind of messaging styles:

## Point to Point

It is the most common, there are producers, ActiveMQ and consumers. Producer put messages on queue
the the consumers listen the queue and get the messages, the message is receive by only one consumer
and most that one producer can put message on queue.

## Publisher & Subscribe 

In progress...