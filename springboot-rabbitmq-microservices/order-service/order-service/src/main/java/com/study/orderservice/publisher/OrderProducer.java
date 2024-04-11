package com.study.orderservice.publisher;

import com.study.orderservice.dto.Order;
import com.study.orderservice.dto.OrderEvent;
import com.study.orderservice.dto.OrderStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class OrderProducer {
    private final static Logger LOGGER = LoggerFactory.getLogger(OrderProducer.class);

    @Value("${rabbitmq.order.exchange}")
    private String orderExchangeName;
    @Value("${rabbitmq.order.routing.key}")
    private String orderRoutingKeyName;

    @Value("${rabbitmq.routing.key.email}")
    private String emailRoutingKeyName;

    private final AmqpTemplate amqpTemplate;

    public OrderProducer(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    public void sendOrder(final Order order) {
        LOGGER.info("order={}", order);
        order.setId(UUID.randomUUID().toString());
        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setOrder(order);
        orderEvent.setMessage("Order created successfully.");
        orderEvent.setStatus(OrderStatus.CREATED);
        orderEvent.setOrderCreationDate(new Date());
        orderEvent.setOrderLastUpdateDate(new Date());
        LOGGER.info(
                "Sending order event: {} to exchange: {} with routingKey: {}",
                orderEvent,
                orderExchangeName,
                orderRoutingKeyName
        );
        amqpTemplate.convertAndSend(orderExchangeName, orderRoutingKeyName, orderEvent);
        LOGGER.info("Order sent successfully to order queue.");
        amqpTemplate.convertAndSend(orderExchangeName, emailRoutingKeyName, orderEvent);
        LOGGER.info("Order event sent successfully to email Queue.");
    }

}
