package com.arka.inventory_service.controller;

import com.arka.inventory_service.dto.request.CurrencyRequestDTO;
import com.arka.inventory_service.dto.request.CurrencyUpdateRequestDTO;
import com.arka.inventory_service.dto.response.CurrencyResponseDTO;
import com.arka.inventory_service.service.ICurrencyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/currencies")
@RequiredArgsConstructor
public class CurrencyController {

    private final ICurrencyService currencyService;

    @PostMapping
    public ResponseEntity<CurrencyResponseDTO> createCurrency(@Valid @RequestBody CurrencyRequestDTO request) {
        CurrencyResponseDTO response = currencyService.createCurrency(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CurrencyResponseDTO> getCurrencyById(@PathVariable UUID id) {
        CurrencyResponseDTO response = currencyService.getCurrencyById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<CurrencyResponseDTO> getCurrencyByCode(@PathVariable String code) {
        CurrencyResponseDTO response = currencyService.getCurrencyByCode(code);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<CurrencyResponseDTO> getCurrencyByName(@PathVariable String name) {
        CurrencyResponseDTO response = currencyService.getCurrencyByName(name);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/symbol")
    public ResponseEntity<List<CurrencyResponseDTO>> getCurrenciesBySymbol(@RequestParam String symbol) {
        List<CurrencyResponseDTO> response = currencyService.getCurrenciesBySymbol(symbol);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<CurrencyResponseDTO>> getAllCurrencies() {
        List<CurrencyResponseDTO> response = currencyService.getAllCurrencies();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CurrencyResponseDTO> updateCurrency(@PathVariable UUID id, @Valid @RequestBody CurrencyUpdateRequestDTO request) {
        CurrencyResponseDTO response = currencyService.updateCurrency(id, request);
        return ResponseEntity.ok(response);
    }
}
