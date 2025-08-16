package com.arka.inventory_service.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
public class StockRequestDTO {

    @NotNull(message = "Product variant is required.")
    private UUID productVariantId;

    @NotNull(message = "Warehouse variant is required.")
    private UUID warehouseId;

    @Min(0)
    @NotNull(message = "Stock is required.")
    private Integer stock;
}
