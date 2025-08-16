package com.arka.inventory_service.dto.request;


import com.arka.inventory_service.enums.ReservationStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
public class StockReservationUpdateRequestDTO {

    private UUID stockMovementId;

    private UUID cartId;

    private LocalDateTime expiresAt;

    private ReservationStatus status;
}
