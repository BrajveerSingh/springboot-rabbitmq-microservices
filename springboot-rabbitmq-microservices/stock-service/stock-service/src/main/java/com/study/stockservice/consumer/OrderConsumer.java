package com.study.stockservice.consumer;

import com.study.stockservice.dto.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderConsumer.class);

    @RabbitListener(queues = "${rabbitmq.order.queue}")
    public void receiveOrder(final OrderEvent orderEvent) {
        LOGGER.info("Order received: {}", orderEvent);
        // Process the order
    }
}
