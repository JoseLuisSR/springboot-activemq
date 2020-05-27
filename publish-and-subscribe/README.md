# Publisher & Subscriber

It is one of my favorites patterns, and is very helpful to communication between applications 
with low coupling. Publish & Subscribe is use in event driven architecture, communication 
between microservices and exchange information between bounded context with Domain Driven 
Design.

Len Bass mention in his book [Software Architecture in Practice](https://www.amazon.com/-/es/Len-Bass/dp/0321815734) that publish and subscribe pattern can take several forms:

"
* List-base, is a realization of the pattern where every publisher maintains subscription list that
have registered interest in receiving the event. This version of the pattern is less decoupled than others
and hence it does not provide as much modifiability, but it can be quite efficient in terms of runtime
overhead. Also, if the components are distributed, there is not single point of failure.

* Broadcast-based, the publishers have less (or no) knowledge of the subscribers
Publishers simply publish events, which are then broadcast. Subscribers examine each event as it arrives
and determine whether the published event is of interest. This version has the potential to be very inefficient
if there are lost of messages and most messages are no of interest to a particular subscriber.

* Content-base, topics are predefined events or messages, and a component subscribers to all
events within the topic. Content, on the other hand, is much more general. Each event is associated with a 
set of attributes and is delivered to a subscribers only if those attributes match subscriber-defined patterns.

"

ActiveMQ use Broadcast-base by default, the others two forms can be implementing through programming logic.

## Architecture

![Screenshot](https://github.com/JoseLuisSR/springboot-activemq/blob/master/doc/img/Publish-Subscribe-FV.png?raw=true)

Publisher send messages to store in ActiveMQ topic, it doesn't care where are the subscribers 
and which ones receive the messages, but needs to know where is the ActiveMQ server. 

ActiveMQ receive messages from publishers and store those in a topic, also record the 
subscribers interested in the topic and dispatch the messages to all subscribers. 

The subscriber listen the ActiveMQ topics and receive the messages, also needs to know where 
is ActiveMQ server and connect to it. Subscribers can register to one or many topic they want.

## Projects

In each project you can find more detail about set up and use ActiveMQ from spring boot.

* [Publisher](https://github.com/JoseLuisSR/springboot-activemq/tree/master/publish-and-subscribe/publisher): Set up embedded ActiveMQ on Spring Boot, exposes RestFUL end-points to put 
messages (JMS and custom objects) on topic through JMS API.

* [Subscriber](https://github.com/JoseLuisSR/springboot-activemq/tree/master/publish-and-subscribe/subscriber): Spring boot application that connect to ActiveMQ and receive the messages 
through topic listeners using JMS API.

One application could be Publisher and Subscriber at the same time and use embedded 
ActiveMQ also.

## Virtual topic

[Virtual Topic](https://activemq.apache.org/virtual-destinations) is a very good capability of ActiveMQ that combine the
best of two worlds, queues and topics.

Messages published in topic are copy to queue and then send to consumer/subscriber that are listening the queue. 
Check [Tomas](https://tuhrig.de/virtual-topics-in-activemq/) post for more information.

Also check the post about [Queue vs. Topics vs. Virtual Topics](https://tuhrig.de/queues-vs-topics-vs-virtual-topics-in-activemq/) 
to know the Pros & Cons of each one and guide you in which cases use one of these.