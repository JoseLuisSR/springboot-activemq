package com.broker.activemq.listeners;

import com.broker.activemq.configurations.ActiveMQConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;

@Component
public class ConsumerText {

    private static final Logger log = LoggerFactory.getLogger(ConsumerText.class);

    @JmsListener(destination = ActiveMQConfiguration.TEXT_QUEUE)
    public void receiveTextMessage(TextMessage textMessage) throws JMSException {
        log.info("Receive text message: " + textMessage.getText() );
    }

}
