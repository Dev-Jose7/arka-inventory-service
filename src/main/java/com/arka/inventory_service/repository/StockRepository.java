package com.arka.inventory_service.repository;

import com.arka.inventory_service.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StockRepository extends JpaRepository<Stock, UUID> {
    List<Stock> findAllByProductId(UUID productId);
    List<Stock> findAllByWarehouseId(UUID warehouseId);
    List<Stock> findAllByStock(Integer stock);
}
