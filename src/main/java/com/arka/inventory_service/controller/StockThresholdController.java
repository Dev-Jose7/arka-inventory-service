package com.arka.inventory_service.controller;

import com.arka.inventory_service.dto.request.StockThresholdRequestDTO;
import com.arka.inventory_service.dto.response.StockThresholdResponseDTO;
import com.arka.inventory_service.service.IStockThresholdService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/stock-thresholds")
@RequiredArgsConstructor
public class StockThresholdController {

    private final IStockThresholdService stockThresholdService;

    @PostMapping
    public ResponseEntity<StockThresholdResponseDTO> createStockThreshold(@Valid @RequestBody StockThresholdRequestDTO request) {
        StockThresholdResponseDTO response = stockThresholdService.createStockThreshold(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockThresholdResponseDTO> getStockThresholdById(@PathVariable UUID id) {
        StockThresholdResponseDTO response = stockThresholdService.getStockThresholdById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/minimum")
    public ResponseEntity<List<StockThresholdResponseDTO>> getStockThresholdsByMinimumQuantity(@RequestParam(name = "minimumQuantity") Integer minimumQuantity) {
        List<StockThresholdResponseDTO> response = stockThresholdService.getStockThresholdsByMinimumQuantity(minimumQuantity);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<StockThresholdResponseDTO>> getAllStockThresholds() {
        List<StockThresholdResponseDTO> response = stockThresholdService.getAllStockThresholds();
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<StockThresholdResponseDTO> updateStockThreshold(@Valid @RequestBody StockThresholdRequestDTO request) {
        StockThresholdResponseDTO response = stockThresholdService.updateStockThreshold(request);
        return ResponseEntity.ok(response);
    }
}
