package com.arka.inventory_service.service;

import com.arka.inventory_service.dto.request.CountryRequestDTO;
import com.arka.inventory_service.dto.response.CountryResponseDTO;

import java.util.List;
import java.util.UUID;

public interface ICountryService {
    CountryResponseDTO createCountry(CountryRequestDTO request);
    CountryResponseDTO getCountryById(UUID id);
    CountryResponseDTO getCountryByName(String name);
    List<CountryResponseDTO> getAllCountries();
}
