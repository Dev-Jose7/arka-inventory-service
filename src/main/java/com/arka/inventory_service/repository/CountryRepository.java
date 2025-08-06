package com.arka.inventory_service.repository;

import com.arka.inventory_service.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CountryRepository extends JpaRepository<Country, UUID> {
    boolean existsByName(String name);
    Optional<Country> findByName(String name);
}
