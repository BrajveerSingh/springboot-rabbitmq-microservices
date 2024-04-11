package com.study.emailservice.consumer;

import com.study.emailservice.dto.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderConsumer.class);

    @RabbitListener(queues = "${rabbitmq.queue.email}")
    public void consumeMessageFromOrderQueue(final OrderEvent orderEvent) {
        LOGGER.info("Message received from order queue : {} ", orderEvent);
        //Send an email to customer for order status
    }

}
