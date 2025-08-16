package com.arka.inventory_service.service.impl;

import com.arka.inventory_service.dto.request.StockReservationUpdateRequestDTO;
import com.arka.inventory_service.dto.response.StockReservationResponseDTO;
import com.arka.inventory_service.enums.ReservationStatus;
import com.arka.inventory_service.exception.ResourceNotFoundException;
import com.arka.inventory_service.mapper.EntityToDTOMapper;
import com.arka.inventory_service.model.StockMovement;
import com.arka.inventory_service.model.StockReservation;
import com.arka.inventory_service.repository.StockMovementRepository;
import com.arka.inventory_service.repository.StockReservationRepository;
import com.arka.inventory_service.service.IStockReservationService;
import com.arka.inventory_service.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StockReservationServiceImpl implements IStockReservationService {

    private final StockReservationRepository reservationRepository;
    private final StockMovementRepository movementRepository;
    private final EntityToDTOMapper mapper;


    @Override
    public StockReservation createReservation(StockMovement stockMovement, UUID cartId, LocalDateTime expiresAt) {
        return createEntity(stockMovement, cartId, expiresAt);
    }

    @Override
    public StockReservationResponseDTO getReservationById(UUID id) {
        return mapper.toDTO(getStockReservationByIdOrException(id));
    }

    @Override
    public List<StockReservationResponseDTO> getReservationsByCartId(UUID cartId) {
        return createResponseList(reservationRepository.findAllByCartId(cartId));
    }

    @Override
    public List<StockReservationResponseDTO> getExpiredReservations(LocalDateTime now) {
        return createResponseList(reservationRepository.findAllByExpiresAtBefore(now));
    }

    @Override
    public List<StockReservationResponseDTO> getReservationsByStatus(ReservationStatus status) {
        return createResponseList(reservationRepository.findAllByStatus(status));
    }

    @Override
    public void updateReservationStatus(UUID id, ReservationStatus status) {
        StockReservation reservation = getStockReservationByIdOrException(id);
        reservation.setStatus(status);
        reservationRepository.save(reservation);
    }

    @Override
    public void updateReservationExpiration(UUID id, LocalDateTime newExpiration) {
        StockReservation reservation = getStockReservationByIdOrException(id);
        reservation.setExpiresAt(newExpiration);
        reservationRepository.save(reservation);
    }

    @Override
    public StockReservationResponseDTO updateReservation(UUID id, StockReservationUpdateRequestDTO request) {
        return mapper.toDTO(updateEntity(id, request));
    }

    // --- Private utility methods ---

    private StockReservation createEntity(StockMovement stockMovement, UUID cartId, LocalDateTime expiresAt) {

        StockReservation reservation = new StockReservation();
        reservation.setStockMovement(stockMovement);
        reservation.setCartId(cartId);
        reservation.setExpiresAt(expiresAt);
        reservation.setStatus(ReservationStatus.ACTIVE);

        return reservationRepository.save(reservation);
    }

    private StockReservation updateEntity(UUID id, StockReservationUpdateRequestDTO request) {
        StockReservation reservation = getStockReservationByIdOrException(id);

        if (ValidationUtil.isValid(request.getCartId(), reservation.getCartId())) {
            reservation.setCartId(request.getCartId());
        }

        if (ValidationUtil.isValid(request.getExpiresAt(), reservation.getExpiresAt())) {
            reservation.setExpiresAt(request.getExpiresAt());
        }

        if (ValidationUtil.isValid(request.getStatus(), reservation.getStatus())) {
            reservation.setStatus(request.getStatus());
        }

        if (ValidationUtil.isValid(request.getStockMovementId(), reservation.getStockMovement().getId())) {
            StockMovement movement = getStockMovementByIdOrException(request.getStockMovementId());
            reservation.setStockMovement(movement);
        }

        return reservationRepository.save(reservation);
    }

    private StockReservation getStockReservationByIdOrException(UUID id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Stock reservation not found."));
    }

    private StockMovement getStockMovementByIdOrException(UUID id) {
        return movementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Stock movement not found."));
    }

    private List<StockReservationResponseDTO> createResponseList(List<StockReservation> reservations) {
        return reservations.stream()
                .map(mapper::toDTO)
                .toList();
    }
}
