package com.arka.inventory_service.controller;

import com.arka.inventory_service.dto.request.WarehouseRequestDTO;
import com.arka.inventory_service.dto.request.WarehouseUpdateRequestDTO;
import com.arka.inventory_service.dto.response.WarehouseResponseDTO;
import com.arka.inventory_service.service.IWarehouseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/warehouses")
@RequiredArgsConstructor
public class WarehouseController {

    private final IWarehouseService warehouseService;

    @PostMapping
    public ResponseEntity<WarehouseResponseDTO> createWarehouse(@Valid @RequestBody WarehouseRequestDTO request) {
        WarehouseResponseDTO response = warehouseService.createWarehouse(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WarehouseResponseDTO> getWarehouseById(@PathVariable UUID id) {
        WarehouseResponseDTO response = warehouseService.getWarehouseById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<WarehouseResponseDTO> getWarehouseByName(@PathVariable String name) {
        WarehouseResponseDTO response = warehouseService.getWarehouseByName(name);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/location")
    public ResponseEntity<List<WarehouseResponseDTO>> getWarehouseByLocation(@RequestParam String location) {
        List<WarehouseResponseDTO> response = warehouseService.getWarehouseByLocation(location);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/country/{id}")
    public ResponseEntity<List<WarehouseResponseDTO>> getWarehouseByCountry(@PathVariable UUID id) {
        List<WarehouseResponseDTO> response = warehouseService.getWarehouseByCountry(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<WarehouseResponseDTO>> getAllWarehouses() {
        List<WarehouseResponseDTO> response = warehouseService.getAllWarehouses();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<WarehouseResponseDTO> updateWarehouse(@PathVariable UUID id, @Valid @RequestBody WarehouseUpdateRequestDTO request) {
        WarehouseResponseDTO response = warehouseService.updateWarehouse(id, request);
        return ResponseEntity.ok(response);
    }
}
