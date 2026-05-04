package com.ibm.inventory_management.services;

import java.util.List;
import java.util.UUID;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.ibm.inventory_management.models.StockItem;
import com.ibm.inventory_management.repositories.StockItemRepository;

@Component
public class StockItemDataInitializer implements ApplicationRunner {

    private final StockItemRepository repository;

    public StockItemDataInitializer(StockItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (this.repository.count() > 0) {
            return;
        }

        this.repository.saveAll(List.of(
                createStockItem("Item 1", "Sony", 10.5, 100),
                createStockItem("Item 2", "Insignia", 100.5, 150),
                createStockItem("Item 3", "Panasonic", 1000.0, 10)));
    }

    private StockItem createStockItem(String name, String manufacturer, double price, int stock) {
        return new StockItem(UUID.randomUUID().toString())
                .withName(name)
                .withManufacturer(manufacturer)
                .withPrice(price)
                .withStock(stock);
    }
}