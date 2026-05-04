package com.ibm.inventory_management.messaging;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class RabbitConfig {

    private final MessagingProperties props;

    public RabbitConfig(MessagingProperties props) {
        this.props = props;
    }

    @Bean
    public TopicExchange stockExchange() {
        return new TopicExchange(props.getExchange(), true, false);
    }

    @Bean
    public TopicExchange stockDeadLetterExchange() {
        return new TopicExchange(props.getDlx(), true, false);
    }

    @Bean
    public Queue stockQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", props.getDlx());
        args.put("x-dead-letter-routing-key", props.getDlqRoutingKey());
        return QueueBuilder.durable(props.getQueue())
                .withArguments(args)
                .build();
    }

    @Bean
    public Queue stockDeadLetterQueue() {
        return QueueBuilder.durable(props.getDlq()).build();
    }

    @Bean
    public Binding stockBinding(Queue stockQueue, TopicExchange stockExchange) {
        return BindingBuilder.bind(stockQueue).to(stockExchange).with(props.getRoutingKey());
    }

    @Bean
    public Binding stockDeadLetterBinding(Queue stockDeadLetterQueue, TopicExchange stockDeadLetterExchange) {
        return BindingBuilder.bind(stockDeadLetterQueue).to(stockDeadLetterExchange).with(props.getDlqRoutingKey());
    }

    @Bean
    public MessageConverter jsonMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        template.setExchange(props.getExchange());
        template.setRoutingKey(props.getRoutingKey());
        return template;
    }
}
