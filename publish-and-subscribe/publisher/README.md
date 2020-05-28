# Publisher

Below are the steps to set up and deploy spring boot application with ActiveMQ embedded and
publish different kind of messages.

## Libraries

* ActiveMQ library is to enable Spring JMS framework.

* Jackson library is to convert objects to/from Json and send like text on a message.

* Lombok is to build getter and setter methods automatically.It is not a dependency for Spring Boot & ActiveMQ.
  You can write getter and setter methods instead.

* Swagger to build Swagger file of the RestFUL end-points.

* In this case publisher also enable embedded ActiveMQ so is necessary add jolokia and hawtio libraries.

```
dependencies {
	implementation 'org.springframework.boot:spring-boot-starter-activemq'
	compile 'com.fasterxml.jackson.core:jackson-databind'
	compile 'org.jolokia:jolokia-core'
	compile 'io.hawt:hawtio-springboot:2.9.1'
	compile('io.springfox:springfox-swagger2:2.7.0')
	compile('io.springfox:springfox-swagger-ui:2.7.0')

	compileOnly 'org.projectlombok:lombok:1.18.12'
	annotationProcessor 'org.projectlombok:lombok:1.18.12'

	testCompileOnly 'org.projectlombok:lombok:1.18.12'
	testAnnotationProcessor 'org.projectlombok:lombok:1.18.12'
	testImplementation('org.springframework.boot:spring-boot-starter-test') {
		exclude group: 'org.junit.vintage', module: 'junit-vintage-engine'
	}
}	
```

# Properties

When [ActiveMQ properties](https://docs.spring.io/spring-boot/docs/current/reference/html/appendix-application-properties.html#integration-properties) 
are present an embedded ActiveMQ broker is started and configured automatically, you can configure connection to remote
broker adding url also.

```
# ActiveMQ properties.

spring.activemq.broker-url=tcp://localhost:61616
spring.jms.pub-sub-domain=true
spring.activemq.packages.trusted=com.broker.activemq.entities,java.lang
spring.activemq.in-memory=true
spring.activemq.close-timeout=15
spring.activemq.password=admin
spring.activemq.user=admin
```

The property `spring.jms.pub-sub-domain` is switch destination type from queue to topic.

ActiveMQ needs define [whitelist packages](https://activemq.apache.org/objectmessage) that can be exchanged using ObjectMessages, so is necessary add the property 
`spring.activemq.packages.trusted` with all the packages used by the class that is going to be send/receive like a message.
You can find details about kind of messages in the next sections.

To expose embedded ActiveMQ over the network you need to add Bean to return BrokerService 
class with connector you need and change the property `spring.activemq.broker-url` wit tha value of the new connector.

```
    @Bean
    public BrokerService broker() throws Exception {
        BrokerService brokerService = new BrokerService();
        brokerService.addConnector("tcp://localhost:61616");
        brokerService.setPersistent(false);
        brokerService.setDestinationPolicy(policyMap());
        return brokerService;
    }
```

```
spring.activemq.broker-url=tcp://localhost:61616
```

Remember that we need use Jolokia and Hawtio to integrate with JMX end-point and get access to ActiveMQ resources and metrics
through Hawtio web console, add the below properties also.

```
    # Hawtio client
    
    management.endpoints.web.exposure.include=hawtio,jolokia
    hawtio.authenticationEnabled=false
```

##Configuration

Spring Boot can automatically configure a ConnectionFactory when it detects ActiveMQ is available on the class-path.
We are using ActiveMQConfiguration class to declare @Beans to get the DefaultJmsListenerContainerFactory and use Jackson
to convert classes to and from JSON. Configuration class has the @EnableJMS that enables detection of methods using 
@JmsListener annotation to call those each time the broker receive a message.

## Controller

Also is necessary define a @RestController to expose Rest/Json end-points to publish messages through @Service classes.

![Screenshot](https://github.com/JoseLuisSR/springboot-activemq/blob/master/doc/img/SwaggerPublisher.png?raw=true)

To see all the details of the end-points go to `http://localhost:8080/swagger-ui.html` after run it project.

## Services

We are using @Service Spring annotation to declare services with the logic to send 
messages to specific topics through JmsTemplate class (simplifies JMS access code). 

You can find the classes definitions in this project.

## Listeners

The subscribers classes are using @JmsListener to receive messages, it annotation create listener container to specific 
ActiveMQ topic and access to it through JMSListenerContainerFactory

You can find the classes definitions in this project

## Messages

ActiveMQ support the below JMS Messages objects to store on topics:

Message type | Content | Purpose |
--- | --- | --- |
TextMessage | A java.lang.String object| Exchanges simple text messages. such as XML and Json |
ObjectMessage | A Serializable object in the Java programming language. | Exchanges Java objects.

When you use ObjectMessage is necessary use Serializable interface to serialization from object to chain of Bytes 
before send it in to message.

Also is possible send custom classes through Jackson that convert from object to JSON 
and send it like text message. 

## Build

Publisher project is using Gradle to resolve dependencies from repositories, also to build jar file, just type in your console


    gradle build 

    
You can find the jar file in the build/libs directory with the name `publisher-x.y.z.jar`

Gradle has the option to build without having to install the Gradle runtime through [Gradle Wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html).

## Run

Just run jar file with the below commando in the console.

    java -jar publisher-x.y.z.jar

## Test

This repository has JMeter test plan configure with HTTP Request to call RestFUL end-points 
of publisher. Examples:

    http://localhost:8080/publisher/customer?name=&age=
    http://localhost:8080/publisher/text/{text}
    http://localhost:8080/publisher/object/{value}
    
    
![Screenshot](https://github.com/JoseLuisSR/springboot-activemq/blob/master/doc/img/JMeterPublish-and-Subscribe.png?raw=true)
    
    
To Access Hawtio console and see the topics size and other metrics then just type the URL 

    http://localhost:8080/actuator/hawtio/
    
![Screenshot](https://github.com/JoseLuisSR/springboot-activemq/blob/master/doc/img/Qhawtio-topics.png?raw=true)