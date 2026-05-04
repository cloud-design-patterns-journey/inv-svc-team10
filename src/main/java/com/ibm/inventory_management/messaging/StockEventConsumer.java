package com.ibm.inventory_management.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.ibm.inventory_management.services.StockItemApi;

@Component
public class StockEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(StockEventConsumer.class);

    private final StockItemApi service;

    public StockEventConsumer(StockItemApi service) {
        this.service = service;
    }

    @RabbitListener(queues = "${inventory.messaging.queue}")
    public void handle(StockEvent event) {
        if (event == null || event.getType() == null) {
            log.error("Invalid event payload — routing to DLQ");
            throw new AmqpRejectAndDontRequeueException("Invalid event payload");
        }

        log.info("Consuming stock event: type={} eventId={} id={}",
                event.getType(), event.getEventId(), event.getId());

        switch (event.getType()) {
            case CREATE -> service.addStockItem(
                    event.getName(),
                    event.getManufacturer(),
                    event.getPrice(),
                    event.getStock());
            case UPDATE -> {
                requireId(event);
                service.updateStockItem(
                        event.getId(),
                        event.getName(),
                        event.getManufacturer(),
                        event.getPrice(),
                        event.getStock());
            }
            case DELETE -> {
                requireId(event);
                service.deleteStockItem(event.getId());
            }
        }
    }

    private void requireId(StockEvent event) {
        if (event.getId() == null || event.getId().isBlank()) {
            log.error("Missing id for event type={} — routing to DLQ", event.getType());
            throw new AmqpRejectAndDontRequeueException("Missing id for " + event.getType());
        }
    }
}
