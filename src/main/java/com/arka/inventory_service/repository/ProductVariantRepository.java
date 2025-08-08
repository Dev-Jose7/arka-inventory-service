package com.arka.inventory_service.repository;

import com.arka.inventory_service.model.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, UUID> {
    boolean existsBySku(String sku);
    boolean existsByNameIgnoreCaseAndProductId(String name, UUID productId);
    long countByProductId(UUID productId);
    Optional<ProductVariant> findBySku(String sku);
    List<ProductVariant> findAllByNameIgnoreCase(String name);
    List<ProductVariant> findAllByProductId(UUID productId);
    List<ProductVariant> findAllByCurrencyId(UUID currencyId);
    List<ProductVariant> findAllByPrice(BigDecimal price);
}