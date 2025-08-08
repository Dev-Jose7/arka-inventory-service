package com.arka.inventory_service.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.*;

@Getter
@Setter
@ToString
public class ProductVariantRequestDTO {

    @NotBlank(message = "Name is required.")
    @Size(max = 100, message = "Name must be at most 100 characters.")
    private String name;

    @NotNull(message = "Product ID is required.")
    private UUID productId;

    @NotNull(message = "Currency ID is required.")
    private UUID currencyId;

    @NotNull(message = "Price is required.")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0.")
    private BigDecimal price;
}
