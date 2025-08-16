package com.arka.inventory_service.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
public class StockUpdateByReservationRequestDTO {

    @NotNull(message = "Product variant ID is required.")
    private UUID productVariantId;

    @NotNull(message = "Cart ID is required.")
    private UUID cartId;

    @NotNull(message = "Quantity is required.")
    private Integer quantity;
}
