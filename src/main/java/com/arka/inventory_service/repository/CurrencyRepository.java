package com.arka.inventory_service.repository;

import com.arka.inventory_service.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, UUID> {
    boolean existsByNameIgnoreCase(String name);
    Optional<Currency> findByName(String name);
    Optional<Currency> findByCode(String name);
    List<Currency> findAllBySymbol(String symbol);
}
