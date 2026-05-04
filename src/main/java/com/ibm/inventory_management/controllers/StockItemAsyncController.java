package com.ibm.inventory_management.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.inventory_management.messaging.StockEvent;
import com.ibm.inventory_management.messaging.StockEventPublisher;

@RestController
@RequestMapping("/async")
public class StockItemAsyncController {

    private final StockEventPublisher publisher;

    public StockItemAsyncController(StockEventPublisher publisher) {
        this.publisher = publisher;
    }

    @PostMapping(path = "/stock-item")
    public ResponseEntity<AcceptedResponse> addStockItem(
            @RequestParam String name,
            @RequestParam String manufacturer,
            @RequestParam double price,
            @RequestParam int stock) {
        StockEvent event = StockEvent.create(name, manufacturer, price, stock);
        publisher.publish(event);
        return ResponseEntity.accepted().body(new AcceptedResponse(event.getEventId(), "queued"));
    }

    @PutMapping(path = "/stock-item/{id}")
    public ResponseEntity<AcceptedResponse> updateStockItem(
            @PathVariable("id") String id,
            @RequestParam String name,
            @RequestParam String manufacturer,
            @RequestParam double price,
            @RequestParam int stock) {
        StockEvent event = StockEvent.update(id, name, manufacturer, price, stock);
        publisher.publish(event);
        return ResponseEntity.accepted().body(new AcceptedResponse(event.getEventId(), "queued"));
    }

    @DeleteMapping(path = "/stock-item/{id}")
    public ResponseEntity<AcceptedResponse> deleteStockItem(@PathVariable("id") String id) {
        StockEvent event = StockEvent.delete(id);
        publisher.publish(event);
        return ResponseEntity.accepted().body(new AcceptedResponse(event.getEventId(), "queued"));
    }

    public record AcceptedResponse(String eventId, String status) {}
}
