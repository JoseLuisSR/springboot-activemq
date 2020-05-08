package com.broker.activemq.services;

import com.broker.activemq.configurations.ActiveMQConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

@Service
public class PublisherTextService {

    private static final Logger log = LoggerFactory.getLogger(PublisherTextService.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    public void publishText(String text){
        log.info("Sending text " + text + " to topic " + ActiveMQConfiguration.TEXT_TOPIC);
        jmsTemplate.send(ActiveMQConfiguration.TEXT_TOPIC, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage();
                textMessage.setText(text);
                return textMessage;
            }
        });
    }

}
