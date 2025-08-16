package com.arka.inventory_service.service.impl;

import com.arka.inventory_service.dto.response.StockMovementResponseDTO;
import com.arka.inventory_service.enums.StockMovementType;
import com.arka.inventory_service.exception.ResourceNotFoundException;
import com.arka.inventory_service.mapper.EntityToDTOMapper;
import com.arka.inventory_service.model.Stock;
import com.arka.inventory_service.model.StockMovement;
import com.arka.inventory_service.model.StockReservation;
import com.arka.inventory_service.repository.StockMovementRepository;
import com.arka.inventory_service.repository.StockRepository;
import com.arka.inventory_service.service.IStockMovementService;
import com.arka.inventory_service.service.IStockReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StockMovementServiceImpl implements IStockMovementService {

    private final StockMovementRepository stockMovementRepository;
    private final IStockReservationService stockReservationService;
    private final StockRepository stockRepository;
    private final EntityToDTOMapper mapper;

    private final Integer RESERVATION_EXPIRATION = 15;

    @Override
    public StockMovement createStockMovement(UUID stockId, StockMovementType type, int quantity, String description) {
        return createEntity(stockId, type, quantity, description);
    }

    @Override
    public StockMovement createStockMovementWithReservation(UUID stockId, UUID cartId, StockMovementType type, int quantity, String description) {
        StockMovement stockMovement = createEntity(stockId, type, quantity, description);
        stockReservationService.createReservation(stockMovement, cartId, LocalDateTime.now().plusMinutes(RESERVATION_EXPIRATION));
        return stockMovement;
    }

    @Override
    public StockMovementResponseDTO getStockMovementById(UUID id) {
        return mapper.toDTO(getStockMovementByIdOrException(id));
    }

    @Override
    public List<StockMovementResponseDTO> getStockMovements() {
        return createResponseList(stockMovementRepository.findAll());
    }

    @Override
    public List<StockMovementResponseDTO> getStockMovementByStockId(UUID stockId) {
        return createResponseList(stockMovementRepository.findAllByStockIdOrderByCreatedAtDesc(stockId));
    }

    @Override
    public List<StockMovementResponseDTO> getStockMovementByType(StockMovementType type) {
        return createResponseList(stockMovementRepository.findAllByType(type));
    }

    @Override
    public List<StockMovementResponseDTO> getStockMovementByQuantity(Integer quantity) {
        return createResponseList(stockMovementRepository.findAllByQuantity(quantity));
    }

    // --- Private utility methods ---

    private StockMovement createEntity(UUID stockId, StockMovementType type, int quantity, String description) {
        Stock stock = getStockByIdOrException(stockId);

        StockMovement movement = new StockMovement();
        movement.setStock(stock);
        movement.setType(type);
        movement.setQuantity(quantity);
        movement.setDescription(description);

        return stockMovementRepository.save(movement);
    }

    private StockMovement getStockMovementByIdOrException(UUID id) {
        return stockMovementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Stock movement not found."));
    }

    private Stock getStockByIdOrException(UUID id) {
        return stockRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Stock not found."));
    }

    private List<StockMovementResponseDTO> createResponseList(List<StockMovement> movementList) {
        return movementList.stream().map(mapper::toDTO).toList();
    }
}
