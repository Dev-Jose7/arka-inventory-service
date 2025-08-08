package com.arka.inventory_service.repository;

import com.arka.inventory_service.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BrandRepository extends JpaRepository<Brand, UUID>{
    boolean existsByNameIgnoreCase(String name);
    Optional<Brand> findByNameIgnoreCase(String name);
}
