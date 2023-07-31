package br.com.compassuol.sp.challenge.msorder.config;

import br.com.compassuol.sp.challenge.msorder.payload.MessageResponse;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "order.v1.events";
    public static final String QUEUE_NAME = "order-confirm";
    public static final String ROUTING_KEY = "order-confirm";


    @Bean
    public Exchange orderExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue orderQueue() {
        return new Queue(QUEUE_NAME);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(orderQueue()).to(orderExchange()).with(ROUTING_KEY).noargs();
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


