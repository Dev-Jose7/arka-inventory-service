package com.arka.inventory_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@Getter
@ToString
public class ProductVariantResponseDTO {
    private UUID id;
    private String sku;
    private String name;
    private ProductResponseDTO product;
    private CurrencyResponseDTO currency;
    private BigDecimal price;
}
