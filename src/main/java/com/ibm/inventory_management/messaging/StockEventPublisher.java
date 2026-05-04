package com.ibm.inventory_management.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class StockEventPublisher {

    private static final Logger log = LoggerFactory.getLogger(StockEventPublisher.class);

    private final RabbitTemplate rabbitTemplate;
    private final MessagingProperties props;

    public StockEventPublisher(RabbitTemplate rabbitTemplate, MessagingProperties props) {
        this.rabbitTemplate = rabbitTemplate;
        this.props = props;
    }

    public void publish(StockEvent event) {
        log.info("Publishing stock event: type={} eventId={} id={}",
                event.getType(), event.getEventId(), event.getId());
        rabbitTemplate.convertAndSend(props.getExchange(), props.getRoutingKey(), event);
    }
}
