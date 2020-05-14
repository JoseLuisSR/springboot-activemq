package com.broker.activemq.services;

import com.broker.activemq.configurations.ActiveMQConfiguration;
import com.broker.activemq.entities.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class PublisherCustomerService {

    private static final Logger log = LoggerFactory.getLogger(PublisherCustomerService.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    public void publishCustomer(String name, Integer age){
        Customer customer = new Customer(name, age);
        log.info("Sending customer message " + customer.toString() + " to topic " + ActiveMQConfiguration.CUSTOMER_TOPIC);
        jmsTemplate.convertAndSend(ActiveMQConfiguration.CUSTOMER_TOPIC, customer);
    }
}
