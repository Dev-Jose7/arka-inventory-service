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
public class StockThresholdRequestDTO {

    @NotNull(message = "Stock ID is required.")
    private UUID stockId;

    @Min(0)
    @NotNull(message = "Minimum quantity is required.")
    private Integer minimumQuantity;
}
