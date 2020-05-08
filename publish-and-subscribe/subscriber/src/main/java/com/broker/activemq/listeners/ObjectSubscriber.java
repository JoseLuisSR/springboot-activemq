package com.broker.activemq.listeners;

import com.broker.activemq.configurations.ActiveMQConfiguration;
import com.broker.activemq.entities.CustomClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;

@Component
public class ObjectSubscriber {

    private static final Logger log = LoggerFactory.getLogger(ObjectSubscriber.class);

    @JmsListener(destination = ActiveMQConfiguration.OBJECT_TOPIC)
    public void receiveObject(ObjectMessage objectMessage) throws JMSException {
        log.info("Receive object message: " + ((CustomClass)objectMessage.getObject()).getValue());
    }

}
