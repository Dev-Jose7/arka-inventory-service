package com.arka.inventory_service.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@ToString
public class ProductVariantUpdateRequestDTO {

    @Size(max = 100, message = "Name must be at most 100 characters.")
    private String name;

    private UUID productId;

    private UUID currencyId;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0.")
    private BigDecimal price;
}
