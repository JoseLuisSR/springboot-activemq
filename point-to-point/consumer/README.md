# Consumer

Below are the steps to set up and deploy spring boot application to consume different kind 
of messages.

## Libraries

To use ActiveMQ from Spring Boot project is necessary use a set of libraries.

* ActiveMQ library is to enable Spring JMS framework.

* Jackson library is to convert objects to/from Json and send like text on a message.

* Lombok is to build getter and setter methods automatically.It is not a dependency for Spring Boot & ActiveMQ.

* Consumer doesn't need jolokia and hawtio libraries because it is not enable embedded ActiveMQ, just connect to it.

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

## Configuration

Spring Boot can automatically configure a ConnectionFactory when it detects ActiveMQ is available on the class-path.
We are using configuration class to declare @Beans methods to get the DefaultJmsListenerContainerFactory and use Jackson
to convert classes to and from JSON. The annotation @EnableJMS allow define classes with method to receive messages with 
@JmsListener annotation.

## Listeners

The @EnableJms annotation enable the receiver class with @JmsListener annotation to create listener container 
to specific ActiveMQ queue and access to it through JMSListenerContainerFactory. Spring inject JMS objects 
at runtime to connect ActiveMQ, access to queue and get the messages.

You can find the classes definitions in this project

## Messages

ActiveMQ support the below JMS Messages objects to store on topics:

Message type | Content | Purpose |
--- | --- | --- |
TextMessage | A java.lang.String object| Exchanges simple text messages. such as XML and Json |
ObjectMessage | A Serializable object in the Java programming language. | Exchanges Java objects.

When you use ObjectMessage is necessary use Serialization interface to deserialization chain of Bytes to object.

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
