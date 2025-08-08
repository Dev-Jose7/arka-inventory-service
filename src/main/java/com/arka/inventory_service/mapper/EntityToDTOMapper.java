package com.arka.inventory_service.mapper;

import com.arka.inventory_service.dto.response.*;
import com.arka.inventory_service.model.*;
import org.springframework.stereotype.Component;

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
                stock.getStock(),
                toDTO(stock.getProductVariant()),
                toDTO(stock.getWarehouse())
        );
    }
}
