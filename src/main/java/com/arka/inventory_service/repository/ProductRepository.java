package com.arka.inventory_service.repository;

import com.arka.inventory_service.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    boolean existsByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCaseAndBrandId(String name, UUID brandId);
    List<Product> findAllByNameIgnoreCase(String name);
    List<Product> findAllByDescriptionIgnoreCase(String description);
    List<Product> findAllByBrandId(UUID brandId);
    List<Product> findAllByCategoryId(UUID categoryId);
}

