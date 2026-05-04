package com.ibm.inventory_management.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ibm.inventory_management.models.StockItem;

public interface StockItemRepository extends JpaRepository<StockItem, String> {
}