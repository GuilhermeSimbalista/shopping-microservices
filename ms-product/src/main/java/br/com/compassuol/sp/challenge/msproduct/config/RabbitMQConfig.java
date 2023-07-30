package br.com.compassuol.sp.challenge.msproduct.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String CONFIRMATION_EXCHANGE_NAME = "confirmation.order.v1.events";
    public static final String CONFIRMATION_QUEUE_NAME = "confirmation.order-confirm";
    public static final String CONFIRMATION_ROUTING_KEY = "confirmation.order-confirm";

    @Bean
    public Exchange orderExchange() {
        return new DirectExchange(CONFIRMATION_EXCHANGE_NAME);
    }

    @Bean
    public Queue orderQueue() {
        return new Queue(CONFIRMATION_QUEUE_NAME);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(orderQueue()).to(orderExchange()).with(CONFIRMATION_ROUTING_KEY).noargs();
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                               Jackson2JsonMessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }
}

