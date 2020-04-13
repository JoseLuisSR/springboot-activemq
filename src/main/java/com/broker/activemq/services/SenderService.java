package com.broker.activemq.services;

import com.broker.activemq.configurations.ActiveMQConfig;
import com.broker.activemq.entities.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class SenderService {

    private static Logger log = LoggerFactory.getLogger(SenderService.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    public void send(Message message){
        log.info("Sending message to queue " + ActiveMQConfig.MESSAGE_QUEUE + ", message:  " + message.toString());
        jmsTemplate.convertAndSend(ActiveMQConfig.MESSAGE_QUEUE, message);
    }
}
