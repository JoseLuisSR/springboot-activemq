package com.broker.activemq.services;

import com.broker.activemq.configurations.ActiveMQConfiguration;
import com.broker.activemq.entities.CustomClass;
import com.broker.activemq.entities.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import javax.jms.*;

@Service
public class ProducerCustomerService {

    private static Logger log = LoggerFactory.getLogger(ProducerCustomerService.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendCustomer(String name, Integer age){
        Customer customer = new Customer(name, age);
        log.info("Sending customer message" + customer.toString() + " to queue " + ActiveMQConfiguration.CUSTOMER_QUEUE);
        jmsTemplate.convertAndSend(ActiveMQConfiguration.CUSTOMER_QUEUE, customer);
    }

}
