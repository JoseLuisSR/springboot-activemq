# Consumer

Below are the steps to set up and deploy spring boot application to consume different kind 
of messages.

## Libraries

To use ActiveMQ from Spring Boot project is necessary use a set of libraries.

* ActiveMQ library is to enable Spring JMS framework.

* Jackson library is to convert objects to/from Json and send like text on a message.

* Lombok is to build getter and setter methods automatically.It is not a dependency for Spring Boot & ActiveMQ.
  You can write getter and setter methods instead.

* Consumer doesn't need jolokia and hawtio libraries because it don't have enable embedded ActiveMQ, just connect to remote broker server.

```
dependencies {
	implementation 'org.springframework.boot:spring-boot-starter-activemq'
	compile 'com.fasterxml.jackson.core:jackson-databind'

	compileOnly 'org.projectlombok:lombok:1.18.12'
	annotationProcessor 'org.projectlombok:lombok:1.18.12'

	testCompileOnly 'org.projectlombok:lombok:1.18.12'
	testAnnotationProcessor 'org.projectlombok:lombok:1.18.12'

	testImplementation('org.springframework.boot:spring-boot-starter-test') {
		exclude group: 'org.junit.vintage', module: 'junit-vintage-engine'
	}
}
```

## Properties

With the below [ActiveMQ properties](https://docs.spring.io/spring-boot/docs/current/reference/html/appendix-application-properties.html#integration-properties) 
you can connect to ActiveMQ server over network.

```
# ActiveMQ properties.

spring.activemq.broker-url=tcp://localhost:61616
spring.activemq.packages.trusted=com.broker.activemq.entities,java.lang
spring.activemq.in-memory=true
spring.activemq.close-timeout=15
spring.activemq.password=admin
spring.activemq.user=admin

```

ActiveMQ needs define [whitelist packages](https://activemq.apache.org/objectmessage) that can be exchanged using ObjectMessages, so is necessary add the property 
`spring.activemq.packages.trusted` with all the packages used by the class that is going to be send/receive like a message.
You can find details about kind of messages in the next sections.

## Configuration

Spring Boot can automatically configure a ConnectionFactory when it detects ActiveMQ is available on the class-path.
We are using configuration class to declare @Beans methods to get the DefaultJmsListenerContainerFactory and use Jackson
to convert classes to and from JSON. Configuration class has the @EnableJMS that enables detection of methods using 
@JmsListener annotation to call those each time the broker receive a message.

## Listeners

The consumer classes are using @JmsListener to receive messages, it annotation create listener container to specific 
ActiveMQ queue and access to it through JMSListenerContainerFactory

You can find the classes definitions in this project

## Messages

ActiveMQ support the below JMS Messages objects to store on topics:

Message type | Content | Purpose |
--- | --- | --- |
TextMessage | A java.lang.String object| Exchanges simple text messages. such as XML and Json |
ObjectMessage | A Serializable object in the Java programming language. | Exchanges Java objects.

When you use ObjectMessage is necessary use Serializable interface to deserialization from chain of Bytes to object.

Also is possible receive classes created by us through Jackson that convert from JSON to object.

## Build

Consumer project is using Gradle to resolve dependencies from repositories, also to build jar file, just type in your console

    gradle build 
    
You can find the jar file in the build/libs directory with the name `consumer-x.y.z.jar`

Gradle has the option to build without having to install the Gradle runtime through [Gradle Wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html).

## Run

Just run jar file with the below commando in the console.

    java -jar consumer-x.y.z.jar

## Test

When you execute producer and send message through JMeter you can see message receives from 
the console.

## Virtual topics

To consume messages from virtual topics the listener method needs use the queue name `Consumer.{ConsumerName}.VirtualTopic.{TopicName}` 
on destination property. The `VirtualTopic.{TopicName}` is the name used by producer to publish messages on topic. 

```
    @JmsListener(destination = "Consumer.A.VirtualTopic.MY-TOPIC-NAME")
    public void receiveCustomer(Customer customer){
        log.info("Received Customer message: " + customer.toString());
    }
```

That's it. Remember virtual topic combine the best of two worlds topic and queue. The messages are going publishing 
on a topic, then ActiveMQ copy the messages from topic to queue (one o more queues) and finally you can use dispatch 
policies to send message to just one consumer and not all consumer like publisher and subscriber message style does.