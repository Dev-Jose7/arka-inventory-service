package com.arka.inventory_service.repository;

import com.arka.inventory_service.enums.StockMovementType;
import com.arka.inventory_service.model.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StockMovementRepository extends JpaRepository<StockMovement, UUID> {

    List<StockMovement> findAllByStockIdOrderByCreatedAtDesc(UUID stockId);
    List<StockMovement> findAllByType(StockMovementType type);
    List<StockMovement> findAllByQuantity(Integer quantity);
}
