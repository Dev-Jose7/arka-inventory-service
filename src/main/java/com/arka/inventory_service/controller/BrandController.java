package com.arka.inventory_service.controller;

import com.arka.inventory_service.dto.request.BrandRequestDTO;
import com.arka.inventory_service.dto.response.BrandResponseDTO;
import com.arka.inventory_service.service.IBrandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/brands")
@RequiredArgsConstructor
public class BrandController {

    private final IBrandService brandService;

    @PostMapping
    public ResponseEntity<BrandResponseDTO> createBrand(@Valid @RequestBody BrandRequestDTO request) {
        BrandResponseDTO response = brandService.createBrand(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BrandResponseDTO> getBrandById(@PathVariable UUID id) {
        BrandResponseDTO response = brandService.getBrandById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<BrandResponseDTO> getBrandByName(@PathVariable String name) {
        BrandResponseDTO response = brandService.getBrandByName(name);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<BrandResponseDTO>> getAllBrands() {
        List<BrandResponseDTO> response = brandService.getAllBrands();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BrandResponseDTO> updateBrand(@PathVariable UUID id, @Valid @RequestBody BrandRequestDTO request) {
        BrandResponseDTO response = brandService.updateBrand(id, request);
        return ResponseEntity.ok(response);
    }

}
