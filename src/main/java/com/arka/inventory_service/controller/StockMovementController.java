package com.arka.inventory_service.controller;

import com.arka.inventory_service.dto.response.StockMovementResponseDTO;
import com.arka.inventory_service.enums.StockMovementType;
import com.arka.inventory_service.service.IStockMovementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/stock-movements")
@RequiredArgsConstructor
public class StockMovementController {

    private final IStockMovementService stockMovementService;

    @GetMapping("/{id}")
    public ResponseEntity<StockMovementResponseDTO> getStockMovementById(@PathVariable UUID id) {
        StockMovementResponseDTO response = stockMovementService.getStockMovementById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<StockMovementResponseDTO>> getAllStockMovements() {
        List<StockMovementResponseDTO> response = stockMovementService.getStockMovements();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/stock/{stockId}")
    public ResponseEntity<List<StockMovementResponseDTO>> getStockMovementsByStockId(@PathVariable UUID stockId) {
        List<StockMovementResponseDTO> response = stockMovementService.getStockMovementByStockId(stockId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<StockMovementResponseDTO>> getStockMovementsByType(@PathVariable StockMovementType type) {
        List<StockMovementResponseDTO> response = stockMovementService.getStockMovementByType(type);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/quantity/{quantity}")
    public ResponseEntity<List<StockMovementResponseDTO>> getStockMovementsByQuantity(@PathVariable Integer quantity) {
        List<StockMovementResponseDTO> response = stockMovementService.getStockMovementByQuantity(quantity);
        return ResponseEntity.ok(response);
    }
}
