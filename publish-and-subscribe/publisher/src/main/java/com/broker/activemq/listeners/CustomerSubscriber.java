package com.broker.activemq.listeners;

import com.broker.activemq.configurations.ActiveMQConfiguration;
import com.broker.activemq.entities.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class CustomerSubscriber {

    private static final Logger log = LoggerFactory.getLogger(CustomerSubscriber.class);

    @JmsListener(destination = ActiveMQConfiguration.CUSTOMER_TOPIC)
    public void receiveCustomer(Customer customer){
        log.info("Received Customer message: " + customer.toString());
    }

}
