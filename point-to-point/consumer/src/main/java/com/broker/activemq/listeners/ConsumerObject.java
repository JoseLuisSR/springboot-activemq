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
public class ConsumerObject {

    private static final Logger log = LoggerFactory.getLogger(ConsumerObject.class);

    @JmsListener(destination = ActiveMQConfiguration.OBJECT_QUEUE)
    public void receiveObjectMessage(ObjectMessage objectMessage) throws JMSException {
        log.info("Receive object message: " + ((CustomClass)objectMessage.getObject()).toString());
    }

}
