package com.arka.inventory_service.controller;

import com.arka.inventory_service.dto.request.CountryRequestDTO;
import com.arka.inventory_service.dto.response.CountryResponseDTO;
import com.arka.inventory_service.service.ICountryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/countries")
@RequiredArgsConstructor
public class CountryController {

    private final ICountryService countryService;

    @PostMapping
    public ResponseEntity<CountryResponseDTO> createCountry(@Valid @RequestBody CountryRequestDTO request) {
        CountryResponseDTO response = countryService.createCountry(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CountryResponseDTO> getCountryById(@PathVariable UUID id) {
        CountryResponseDTO response = countryService.getCountryById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<CountryResponseDTO> getCountryByName(@PathVariable String name) {
        CountryResponseDTO response = countryService.getCountryByName(name);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<CountryResponseDTO>> getAllCountries() {
        List<CountryResponseDTO> response = countryService.getAllCountries();
        return ResponseEntity.ok(response);
    }
}
