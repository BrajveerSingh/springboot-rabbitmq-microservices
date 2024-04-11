package com.study.orderservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Value("${rabbitmq.order.queue}")
    private String orderQueueName;
    @Value("${rabbitmq.order.exchange}")
    private String orderExchangeName;
    @Value("${rabbitmq.order.routing.key}")
    private String orderRoutingKeyName;

    @Value("${rabbitmq.queue.email}")
    private String emailQueueName;
    @Value("${rabbitmq.routing.key.email}")
    private String emailRoutingKeyName;

    @Bean
    public Queue orderQueue() {
        return new Queue(orderQueueName, true);
    }

    @Bean
    public Queue emailQueue() {
        return new Queue(emailQueueName, true);
    }


    @Bean
    public Exchange orderExchange() {
        return new TopicExchange(orderExchangeName);
    }

    @Bean
    public Binding orderBinding(final Queue orderQueue, final Exchange orderExchange) {
        return BindingBuilder
                .bind(orderQueue)
                .to(orderExchange)
                .with(orderRoutingKeyName)
                .noargs();
    }

    @Bean
    public Binding emailBinding(final Queue emailQueue, final Exchange orderExchange) {
        return BindingBuilder
                .bind(emailQueue)
                .to(orderExchange)
                .with(emailRoutingKeyName)
                .noargs();
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(final ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

}
