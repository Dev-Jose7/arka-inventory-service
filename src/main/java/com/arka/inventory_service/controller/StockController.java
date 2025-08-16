package com.arka.inventory_service.controller;

import com.arka.inventory_service.dto.request.StockRequestDTO;
import com.arka.inventory_service.dto.request.StockUpdateByReservationRequestDTO;
import com.arka.inventory_service.dto.request.StockUpdateByWithdrawRequestDTO;
import com.arka.inventory_service.dto.request.StockUpdateRequestDTO;
import com.arka.inventory_service.dto.response.StockByCheckoutResponseDTO;
import com.arka.inventory_service.dto.response.StockCartValidationResponseDTO;
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

    @GetMapping("/validate-cart/{cartId}")
    public ResponseEntity<List<StockCartValidationResponseDTO>> validateCart(@PathVariable UUID cartId) {
        List<StockCartValidationResponseDTO> response = stockService.validateCartStock(cartId);
        return ResponseEntity.ok(response);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<StockResponseDTO> updateStock(@PathVariable UUID id, @Valid @RequestBody StockUpdateRequestDTO request) {
        StockResponseDTO response = stockService.updateStock(id, request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Void> withdrawStock(@Valid @RequestBody StockUpdateByWithdrawRequestDTO request) {
        stockService.updateStockByWithdraw(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reservation")
    public ResponseEntity<Void> updateStockByReservation(@Valid @RequestBody StockUpdateByReservationRequestDTO resquest) {
        stockService.updateStockByReservation(resquest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/finalize-reservation/{cartId}")
    public ResponseEntity<List<StockByCheckoutResponseDTO>> finalizeStockReservationByCheckout(@PathVariable UUID cartId) {
        List<StockByCheckoutResponseDTO> response = stockService.finalizeStockReservationByCheckout(cartId);
        return ResponseEntity.ok(response);
    }
}
