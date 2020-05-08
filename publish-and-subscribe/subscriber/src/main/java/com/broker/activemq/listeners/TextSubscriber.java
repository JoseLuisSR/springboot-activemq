package com.broker.activemq.listeners;

import com.broker.activemq.configurations.ActiveMQConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;

@Component
public class TextSubscriber {

    private static final Logger log = LoggerFactory.getLogger(TextSubscriber.class);

    @JmsListener(destination = ActiveMQConfiguration.TEXT_TOPIC)
    public void receiveText(TextMessage textMessage) throws JMSException {
        log.info("Receive text message: " + textMessage.getText());
    }
}
