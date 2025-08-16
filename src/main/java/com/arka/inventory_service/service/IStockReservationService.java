package com.arka.inventory_service.service;

import com.arka.inventory_service.dto.request.StockReservationRequestDTO;
import com.arka.inventory_service.dto.request.StockReservationUpdateRequestDTO;
import com.arka.inventory_service.dto.response.StockReservationResponseDTO;
import com.arka.inventory_service.enums.ReservationStatus;
import com.arka.inventory_service.model.StockMovement;
import com.arka.inventory_service.model.StockReservation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface IStockReservationService {

    StockReservation createReservation(StockMovement stockMovement, UUID cartId, LocalDateTime expiresAt);
    StockReservationResponseDTO getReservationById(UUID id);
    List<StockReservationResponseDTO> getReservationsByCartId(UUID cartId);
    List<StockReservationResponseDTO> getExpiredReservations(LocalDateTime now);
    List<StockReservationResponseDTO> getReservationsByStatus(ReservationStatus status);
    void updateReservationStatus(UUID id, ReservationStatus status);
    void updateReservationExpiration(UUID id, LocalDateTime newExpiration);
    StockReservationResponseDTO updateReservation(UUID id, StockReservationUpdateRequestDTO request);
}
