package com.arka.inventory_service.controller;

import com.arka.inventory_service.dto.request.ProductVariantRequestDTO;
import com.arka.inventory_service.dto.request.ProductVariantUpdateRequestDTO;
import com.arka.inventory_service.dto.response.ProductVariantResponseDTO;
import com.arka.inventory_service.service.IProductVariantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/product-variants")
@RequiredArgsConstructor
public class ProductVariantController {

    private final IProductVariantService productVariantService;

    @PostMapping
    public ResponseEntity<ProductVariantResponseDTO> createProductVariant(
            @Valid @RequestBody ProductVariantRequestDTO request) {
        ProductVariantResponseDTO response = productVariantService.createProductVariant(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductVariantResponseDTO>> getAllProductVariants() {
        List<ProductVariantResponseDTO> response = productVariantService.getAllProducts();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/sku/{sku}")
    public ResponseEntity<ProductVariantResponseDTO> getProductVariantBySku(@PathVariable String sku) {
        ProductVariantResponseDTO response = productVariantService.getProductVariantBySku(sku);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<ProductVariantResponseDTO>> getVariantsByName(@PathVariable String name) {
        List<ProductVariantResponseDTO> response = productVariantService.getProductsVariantByName(name);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ProductVariantResponseDTO>> getVariantsByProductId(@PathVariable UUID productId) {
        List<ProductVariantResponseDTO> response = productVariantService.getProductsVariantByProductId(productId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/currency/{currencyId}")
    public ResponseEntity<List<ProductVariantResponseDTO>> getVariantsByCurrencyId(@PathVariable UUID currencyId) {
        List<ProductVariantResponseDTO> response = productVariantService.getProductsVariantByCurrencyId(currencyId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/price")
    public ResponseEntity<List<ProductVariantResponseDTO>> getVariantsByPrice(@RequestParam BigDecimal price) {
        List<ProductVariantResponseDTO> response = productVariantService.getProductsVariantByPrice(price);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductVariantResponseDTO> updateProductVariant(@PathVariable UUID id, @Valid @RequestBody ProductVariantUpdateRequestDTO request) {
        ProductVariantResponseDTO response = productVariantService.updateProductVariant(id, request);
        return ResponseEntity.ok(response);
    }
}
