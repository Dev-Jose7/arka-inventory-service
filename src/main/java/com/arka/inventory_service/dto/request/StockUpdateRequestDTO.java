package com.arka.inventory_service.dto.request;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
public class StockUpdateRequestDTO {

    private UUID productVariantId;

    private UUID warehouseId;

    @Min(0)
    private Integer stock;
}
