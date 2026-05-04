package com.ibm.inventory_management.models;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "stock_items")
public class StockItem implements Serializable {
    @Id
    @Column(nullable = false, updatable = false)
    private String id = null;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int stock = 0;

    @Column(nullable = false)
    private double price = 0.0;

    @Column(nullable = false)
    private String manufacturer = "";

    public StockItem() {
        super();
    }

    public StockItem(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StockItem withName(String name) {
        this.setName(name);
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public StockItem withId(String id) {
        this.setId(id);
        return this;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public StockItem withStock(int stock) {
        this.setStock(stock);
        return this;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public StockItem withPrice(double price) {
        this.setPrice(price);
        return this;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public StockItem withManufacturer(String manufacturer) {
        this.setManufacturer(manufacturer);
        return this;
    }
}