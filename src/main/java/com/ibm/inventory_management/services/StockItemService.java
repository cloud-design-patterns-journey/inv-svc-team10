package com.ibm.inventory_management.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ibm.inventory_management.models.StockItem;
import com.ibm.inventory_management.repositories.StockItemRepository;

@Service
public class StockItemService implements StockItemApi {
    private final StockItemRepository repository;

    public StockItemService(StockItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<StockItem> listStockItems() {
        return this.repository.findAll();
    }

    @Override
    public void addStockItem(String name, String manufacturer, double price, int stock) {
        this.repository.save(new StockItem(UUID.randomUUID().toString())
                .withName(name)
                .withStock(stock)
                .withPrice(price)
                .withManufacturer(manufacturer));
    }

    @Override
    public void updateStockItem(String id, String name, String manufacturer, double price, int stock) {
        this.repository.findById(id).ifPresentOrElse(itemToUpdate -> {
            itemToUpdate.setName(name);
            itemToUpdate.setManufacturer(manufacturer);
            itemToUpdate.setPrice(price);
            itemToUpdate.setStock(stock);
            this.repository.save(itemToUpdate);
        }, () -> System.out.println("Item not found"));
    }

    @Override
    public void deleteStockItem(String id) {
        this.repository.deleteById(id);
    }
}