package com.arka.inventory_service.mapper;

import com.arka.inventory_service.dto.response.*;
import com.arka.inventory_service.model.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class EntityToDTOMapper {

    public BrandResponseDTO toDTO(Brand brand) {
        return new BrandResponseDTO(
                brand.getId(),
                brand.getName()
        );
    }

    public CategoryResponseDTO toDTO(Category category) {
        return new CategoryResponseDTO(
                category.getId(),
                category.getName(),
                category.getDescription()
        );
    }

    public CountryResponseDTO toDTO(Country country) {
        return new CountryResponseDTO(
                country.getId(),
                country.getName()
        );
    }

    public WarehouseResponseDTO toDTO(Warehouse warehouse) {
        return new WarehouseResponseDTO(
                warehouse.getId(),
                warehouse.getName(),
                warehouse.getLocation(),
                toDTO(warehouse.getCountry())
        );
    }

    public ProductResponseDTO toDTO(Product product) {
        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                toDTO(product.getBrand()),
                toDTO(product.getCategory())
        );
    }

    public CurrencyResponseDTO toDTO(Currency currency) {
        return new CurrencyResponseDTO(
                currency.getId(),
                currency.getCode()
        );
    }

    public ProductVariantResponseDTO toDTO(ProductVariant productVariant) {
        return new ProductVariantResponseDTO(
                productVariant.getId(),
                productVariant.getSku(),
                productVariant.getName(),
                toDTO(productVariant.getProduct()),
                toDTO(productVariant.getCurrency()),
                productVariant.getPrice()
        );
    }

    public StockResponseDTO toDTO(Stock stock) {
        return new StockResponseDTO(
                stock.getId(),
                stock.getUnit(),
                toDTO(stock.getProductVariant()),
                toDTO(stock.getWarehouse())
        );
    }

    public StockMovementResponseDTO toDTO(StockMovement stockMovement) {
        return new StockMovementResponseDTO(
                stockMovement.getId(),
                stockMovement.getType(),
                stockMovement.getQuantity(),
                stockMovement.getDescription(),
                toDTO(stockMovement.getStock())
        );
    }

    public StockReservationResponseDTO toDTO(StockReservation stockReservation) {
        return new StockReservationResponseDTO(
                stockReservation.getId(),
                toDTO(stockReservation.getStockMovement()),
                stockReservation.getCartId(),
                stockReservation.getExpiresAt(),
                stockReservation.getStatus()
        );
    }

    public StockByCheckoutResponseDTO toDTO(Stock stock, StockReservation stockReservation) {
        return new StockByCheckoutResponseDTO(
                stock.getId(),
                stockReservation.getStockMovement().getQuantity(),
                stock.getProductVariant().getPrice().multiply(
                        BigDecimal.valueOf(stockReservation.getStockMovement().getQuantity())
                ),
                toDTO(stock.getWarehouse()),
                toDTO(stock.getProductVariant())
        );
    }

    public StockThresholdResponseDTO toDTO(StockThreshold stockThreshold) {
        return new StockThresholdResponseDTO(
                stockThreshold.getId(),
                stockThreshold.getMinimumQuantity(),
                toDTO(stockThreshold.getStock())
        );
    }
}
