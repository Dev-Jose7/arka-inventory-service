package com.arka.inventory_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockCartValidationResponseDTO {

    private UUID stockId;
    private UUID productVariantId;
    private String productName;
    private String variantName;
    private Integer requestedQuantity;
    private Integer availableQuantity;
    private boolean available;
}
