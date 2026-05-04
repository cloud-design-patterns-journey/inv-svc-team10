package com.ibm.inventory_management.messaging;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

public class StockEvent implements Serializable {

    public enum Type { CREATE, UPDATE, DELETE }

    private String eventId;
    private Instant occurredAt;
    private Type type;
    private String id;
    private String name;
    private String manufacturer;
    private double price;
    private int stock;

    public StockEvent() {
        this.eventId = UUID.randomUUID().toString();
        this.occurredAt = Instant.now();
    }

    public static StockEvent create(String name, String manufacturer, double price, int stock) {
        StockEvent event = new StockEvent();
        event.type = Type.CREATE;
        event.name = name;
        event.manufacturer = manufacturer;
        event.price = price;
        event.stock = stock;
        return event;
    }

    public static StockEvent update(String id, String name, String manufacturer, double price, int stock) {
        StockEvent event = new StockEvent();
        event.type = Type.UPDATE;
        event.id = id;
        event.name = name;
        event.manufacturer = manufacturer;
        event.price = price;
        event.stock = stock;
        return event;
    }

    public static StockEvent delete(String id) {
        StockEvent event = new StockEvent();
        event.type = Type.DELETE;
        event.id = id;
        return event;
    }

    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }

    public Instant getOccurredAt() { return occurredAt; }
    public void setOccurredAt(Instant occurredAt) { this.occurredAt = occurredAt; }

    public Type getType() { return type; }
    public void setType(Type type) { this.type = type; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getManufacturer() { return manufacturer; }
    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}
