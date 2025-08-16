package com.arka.inventory_service.service;

import com.arka.inventory_service.dto.response.StockMovementResponseDTO;
import com.arka.inventory_service.enums.StockMovementType;
import com.arka.inventory_service.model.StockMovement;

import java.util.List;
import java.util.UUID;

public interface IStockMovementService {

    StockMovement createStockMovement(UUID stockId, StockMovementType type, int quantity, String description);
    StockMovement createStockMovementWithReservation(UUID stockId, UUID cartId, StockMovementType type, int quantity, String description);
    StockMovementResponseDTO getStockMovementById(UUID id);
    List<StockMovementResponseDTO> getStockMovements();
    List<StockMovementResponseDTO> getStockMovementByStockId(UUID stockId);
    List<StockMovementResponseDTO> getStockMovementByType(StockMovementType type);
    List<StockMovementResponseDTO> getStockMovementByQuantity(Integer quantity);
}
