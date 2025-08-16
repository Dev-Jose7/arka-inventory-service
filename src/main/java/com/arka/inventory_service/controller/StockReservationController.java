package com.arka.inventory_service.controller;

import com.arka.inventory_service.dto.request.StockReservationRequestDTO;
import com.arka.inventory_service.dto.response.StockReservationResponseDTO;
import com.arka.inventory_service.enums.ReservationStatus;
import com.arka.inventory_service.service.IStockReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/stock-reservations")
@RequiredArgsConstructor
public class StockReservationController {

    private final IStockReservationService reservationService;

    @GetMapping("/{id}")
    public ResponseEntity<StockReservationResponseDTO> getReservationById(@PathVariable UUID id) {
        StockReservationResponseDTO response = reservationService.getReservationById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cart/{cartId}")
    public ResponseEntity<List<StockReservationResponseDTO>> getReservationsByCartId(@PathVariable UUID cartId) {
        List<StockReservationResponseDTO> response = reservationService.getReservationsByCartId(cartId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/expired")
    public ResponseEntity<List<StockReservationResponseDTO>> getExpiredReservations(
            @RequestParam("before") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime before
    ) {
        List<StockReservationResponseDTO> response = reservationService.getExpiredReservations(before);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<StockReservationResponseDTO>> getReservationsByStatus(
            @PathVariable ReservationStatus status
    ) {
        List<StockReservationResponseDTO> response = reservationService.getReservationsByStatus(status);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateReservationStatus(
            @PathVariable UUID id,
            @RequestParam ReservationStatus status
    ) {
        reservationService.updateReservationStatus(id, status);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/expiration")
    public ResponseEntity<Void> updateReservationExpiration(
            @PathVariable UUID id,
            @RequestParam("newExpiration")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime newExpiration
    ) {
        reservationService.updateReservationExpiration(id, newExpiration);
        return ResponseEntity.noContent().build();
    }
}
