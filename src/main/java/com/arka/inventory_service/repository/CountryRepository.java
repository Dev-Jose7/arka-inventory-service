package com.arka.inventory_service.repository;

import com.arka.inventory_service.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CountryRepository extends JpaRepository<Country, UUID> {
    Optional<Country> findByName(String name);
    boolean existsByName(String name);
}
