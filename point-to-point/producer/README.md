# Producer

Below are the steps to set up and deploy spring boot application with ActiveMQ embedded and
produce different kind of messages.

## Libraries

* ActiveMQ library is to enable Spring JMS framework.

* Jackson library is to convert objects to/from Json and send like text on a message.

* Lombok is to build getter and setter methods automatically.It is not a dependency for Spring Boot & ActiveMQ.
  You can write getter and setter methods instead.

* Swagger to build Swagger file of the RestFUL end-points.

* In this case producer also enable embedded ActiveMQ so is necessary add jolokia and hawtio libraries.

```
dependencies {
	implementation 'org.springframework.boot:spring-boot-starter-activemq'
	compile 'org.apache.activemq:activemq-broker'
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

## Properties

When [ActiveMQ properties](https://docs.spring.io/spring-boot/docs/current/reference/html/appendix-application-properties.html#integration-properties) 
are present an embedded ActiveMQ broker is started and configured automatically, you can configure connection to remote
broker adding url also.

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

## Configuration

Spring Boot can automatically configure a ConnectionFactory when it detects ActiveMQ is available on the class-path.
We are using ActiveMQConfiguration class to declare @Beans to get the DefaultJmsListenerContainerFactory and use Jackson
to convert classes to and from JSON. Configuration class has the @EnableJMS that enables detection of methods using 
@JmsListener annotation to call those each time the broker receive a message.

To set up dispatch policies like Round Robin and Strict Order is necessary registry Bean to return PolicyMap:

```
    @Bean
    public PolicyMap policyMap(){
        PolicyMap destinationPoliciy = new PolicyMap();
        List<PolicyEntry> entries = new ArrayList<PolicyEntry>();
        PolicyEntry queueEntry = new PolicyEntry();
        queueEntry.setQueue(">");
        queueEntry.setStrictOrderDispatch(false);
        entries.add(queueEntry);
        destinationPoliciy.setPolicyEntries(entries);
        return destinationPoliciy;
    }
```

To use Strict Order dispatch policy just put `true`.

## Controller

Also is necessary define a @RestController to expose Rest/Json end-points to produce messages through @Service classes.

![Screenshot](https://github.com/JoseLuisSR/springboot-activemq/blob/master/doc/img/SwaggerProducer.png?raw=true)

To see all the details of the end-points go to `http://localhost:8080/swagger-ui.html` after run it project.

## Services

We are using @Service Spring annotation to declare services with the logic to send 
messages to specific queue through JmsTemplate class (simplifies JMS access code). 

You can find the classes definitions in this project.

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

When you use ObjectMessage is necessary use Serializable interface to serialization from object to chain of Bytes 
before send it in to message.

Also is possible send custom classes through Jackson that convert from object to JSON 
and send it like text message.  

## Build

Producer project is using Gradle to resolve dependencies from repositories, also to build jar file, just type in your console


    gradle build 

    
You can find the jar file in the build/libs directory with the name `producer-x.y.z.jar`

Gradle has the option to build without having to install the Gradle runtime through [Gradle Wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html).

## Run

Just run jar file with the below commando in the console.

    java -jar producer-x.y.z.jar

## Test

This repository has JMeter test plan configure with HTTP Request to call RestFUL end-points 
of producer. Examples:

    http://localhost:8080/producer/customer?name=&age=
    http://localhost:8080/producer/text/{text}
    http://localhost:8080/producer/object/{value}
    
    
![Screenshot](https://github.com/JoseLuisSR/springboot-activemq/blob/master/doc/img/JMeterPoint-to-Point.png?raw=true)
    
    
To Access Hawtio console and see the queue size and other metrics then just type the URL 

    http://localhost:8080/actuator/hawtio/
    
![Screenshot](https://github.com/JoseLuisSR/springboot-activemq/blob/master/doc/img/Qhawtio-queues.png?raw=true)

## Virtual topics

To publish messages in virtual topics is necessary name the topics as `VirtualTopic.{TopicName}`:

```
    public void publishCustomer(String name, Integer age){
        Customer customer = new Customer(name, age);
        log.info("Sending customer message " + customer.toString() + " to virtual topic " + VirtualTopic.MY-TOPIC-NAME);
        jmsTemplate.convertAndSend(new ActiveMQTopic("VirtualTopic.MY-TOPIC-NAME"), customer);
    }
```
 
The listener method needs use queue name `Consumer.{ConsumerName}.VirtualTopic.{TopicName}` on destination property. 
The `VirtualTopic.{TopicName}` is the name used by producer to publish messages on topic. 

```
    @JmsListener(destination = "Consumer.A.VirtualTopic.MY-TOPIC-NAME")
    public void receiveCustomer(Customer customer){
        log.info("Received Customer message: " + customer.toString());
    }
```

That's it. Remember virtual topic combine the best of two worlds topic and queue. The messages are going publishing 
on a topic, then ActiveMQ copy the messages from topic to queue (one o more queues) and finally you can use dispatch 
policies to send message to just one consumer and not all consumer like publisher and subscriber message style does.