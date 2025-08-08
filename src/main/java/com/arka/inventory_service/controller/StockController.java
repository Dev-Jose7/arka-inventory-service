package com.arka.inventory_service.controller;

import com.arka.inventory_service.dto.request.StockRequestDTO;
import com.arka.inventory_service.dto.response.StockResponseDTO;
import com.arka.inventory_service.service.IStockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
public class StockController {

    private final IStockService stockService;

    @PostMapping
    public ResponseEntity<StockResponseDTO> createStock(@Valid @RequestBody StockRequestDTO request) {
        StockResponseDTO response = stockService.createStock(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockResponseDTO> getStockById(@PathVariable UUID id) {
        StockResponseDTO response = stockService.getStockById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/variant/{variantId}")
    public ResponseEntity<List<StockResponseDTO>> getStockByProductVariantId(@PathVariable UUID variantId) {
        List<StockResponseDTO> response = stockService.getStockByProductVariantId(variantId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/warehouse/{warehouseId}")
    public ResponseEntity<List<StockResponseDTO>> getStockByWarehouseId(@PathVariable UUID warehouseId) {
        List<StockResponseDTO> response = stockService.getStockByWarehouseId(warehouseId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<StockResponseDTO>> getAllStocks() {
        List<StockResponseDTO> response = stockService.getAllStocks();
        return ResponseEntity.ok(response);
    }
}
