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
public class ProducerTextService {

    private static final Logger log = LoggerFactory.getLogger(ProducerCustomerService.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendText(String text){
        log.info("Sending text message with data" + text + " to queue " + ActiveMQConfiguration.TEXT_QUEUE);
        jmsTemplate.send(ActiveMQConfiguration.TEXT_QUEUE, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage();
                textMessage.setText(text);
                return textMessage;
            }
        });
    }

}
