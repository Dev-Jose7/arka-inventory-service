package com.arka.inventory_service.repository;

import com.arka.inventory_service.model.StockThreshold;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StockThresholdRepository extends JpaRepository<StockThreshold, UUID> {
    List<StockThreshold> findAllByMinimumQuantityLessThanEqual(Integer quantity);
}
