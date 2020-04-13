package com.broker.activemq.services;

import com.broker.activemq.configurations.ActiveMQConfig;
import com.broker.activemq.entities.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class ReceiverService {

    private static Logger logger = LoggerFactory.getLogger(ReceiverService.class);

    @JmsListener(destination = ActiveMQConfig.MESSAGE_QUEUE)
    public void receiveMessage(Message message){
        logger.info("Received < " + message.toString() + " >");
    }

}
