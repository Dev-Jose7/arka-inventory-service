package com.arka.inventory_service.repository;

import com.arka.inventory_service.model.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, UUID> {
    boolean existsBySku(String sku);
    List<ProductVariant> findByProductId(UUID productId);
    List<ProductVariant> findByWarehouseId(UUID warehouseId);
}

