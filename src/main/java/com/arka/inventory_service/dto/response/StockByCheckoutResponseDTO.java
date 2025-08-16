package com.arka.inventory_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@Getter
@ToString
public class StockByCheckoutResponseDTO {
    private UUID id;
    private Integer quantity;
    private BigDecimal total;
    private WarehouseResponseDTO warehouse;
    private ProductVariantResponseDTO productVariant;
}
