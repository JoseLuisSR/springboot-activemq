package com.broker.activemq.services;

import com.broker.activemq.configurations.ActiveMQConfiguration;
import com.broker.activemq.entities.CustomClass;
import com.broker.activemq.entities.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.*;
import java.io.Serializable;

@Service
public class ProducerService {

    private static Logger log = LoggerFactory.getLogger(ProducerService.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendCustomer(Customer customer){
        log.info("Sending customer " + customer.toString() + " to queue " + ActiveMQConfiguration.CUSTOMER_QUEUE);
        jmsTemplate.convertAndSend(ActiveMQConfiguration.CUSTOMER_QUEUE, customer);
    }

    public void sendText(String text){
        log.info("Sending text " + text + " to queue " + ActiveMQConfiguration.TEXT_QUEUE);
        jmsTemplate.send(ActiveMQConfiguration.TEXT_QUEUE, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage();
                textMessage.setText(text);
                return textMessage;
            }
        });
    }

    public void sendObject(CustomClass object){
        log.info("Send object " + object.toString() + " to queue " + ActiveMQConfiguration.OBJECT_QUEUE );
        jmsTemplate.send(ActiveMQConfiguration.OBJECT_QUEUE, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                ObjectMessage objectMessage = session.createObjectMessage();
                objectMessage.setObject(object);
                return objectMessage;
            }
        });
    }

}
