package com.arka.inventory_service.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
public class StockReservationRequestDTO {

    @NotNull(message = "Stock Movement ID is required.")
    private UUID stockMovementId;

    @NotNull(message = "Cart ID is required.")
    private UUID cartId;

    @NotNull(message = "Expires at is required.")
    private LocalDateTime expiresAt;
}
