package com.arka.inventory_service.service;

import com.arka.inventory_service.dto.request.CurrencyRequestDTO;
import com.arka.inventory_service.dto.response.CurrencyResponseDTO;

import java.util.Currency;
import java.util.List;
import java.util.UUID;

public interface ICurrencyService {
    CurrencyResponseDTO createCurrency(CurrencyRequestDTO request);
    CurrencyResponseDTO getCurrencyById(UUID id);
    CurrencyResponseDTO getCurrencyByCode(String code);
    CurrencyResponseDTO getCurrencyByName(String name);
    List<CurrencyResponseDTO> getCurrenciesBySymbol(String symbol);
    List<CurrencyResponseDTO> getAllCurrencies();
}
