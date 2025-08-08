package com.arka.inventory_service.repository;

import com.arka.inventory_service.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, UUID> {
    boolean existsByNameIgnoreCase(String name);
    Optional<Warehouse> findByNameIgnoreCase(String name);
    List<Warehouse> findAllByLocationIgnoreCase(String location);
    List<Warehouse> findAllByCountryId(UUID id);
}
