package com.arka.inventory_service.dto.response;

import com.arka.inventory_service.enums.StockMovementType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@AllArgsConstructor
@Getter
@ToString
public class StockMovementResponseDTO {
    private UUID id;
    private StockMovementType type;
    private Integer quantity;
    private String description;
    private StockResponseDTO stock;
}
