package com.arka.inventory_service.dto.response;

import com.arka.inventory_service.enums.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Getter
@ToString
public class StockReservationResponseDTO {
    private UUID id;
    private StockMovementResponseDTO stockMovement;
    private UUID cartId;
    private LocalDateTime expiresAt;
    private ReservationStatus status;
}
