package com.broker.activemq.listeners;

import com.broker.activemq.configurations.ActiveMQConfiguration;
import com.broker.activemq.entities.CustomClass;
import com.broker.activemq.entities.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

@Component
public class ConsumerComponent {

    private Logger log = LoggerFactory.getLogger(ConsumerComponent.class);

    @JmsListener(destination = ActiveMQConfiguration.CUSTOMER_QUEUE)
    public void receiveCustomer(Customer customer){
        log.info("Received Customer message: " + customer.toString());
    }

    @JmsListener(destination = ActiveMQConfiguration.TEXT_QUEUE)
    public void receiveText(TextMessage textMessage) throws JMSException {
        log.info("Received text message: " + textMessage.getText());
    }

    @JmsListener(destination = ActiveMQConfiguration.OBJECT_QUEUE)
    public void receiveObject(ObjectMessage objectMessage) throws JMSException {
        log.info("Received object message: " + ((CustomClass)objectMessage.getObject()).toString());
    }

}
