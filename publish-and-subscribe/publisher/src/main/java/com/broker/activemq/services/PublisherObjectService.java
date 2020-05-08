package com.broker.activemq.services;

import com.broker.activemq.configurations.ActiveMQConfiguration;
import com.broker.activemq.entities.CustomClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

@Service
public class PublisherObjectService {

    private static final Logger log = LoggerFactory.getLogger(PublisherObjectService.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    public void publishObject(String value){
        log.info("Send object with value " +  value + " to topic " +  ActiveMQConfiguration.OBJECT_TOPIC);
        jmsTemplate.send(ActiveMQConfiguration.OBJECT_TOPIC, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                ObjectMessage objectMessage = session.createObjectMessage();
                objectMessage.setObject(new CustomClass(value));
                return objectMessage;
            }
        });
    }

}
