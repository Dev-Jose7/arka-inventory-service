package com.arka.inventory_service.service.impl;

import com.arka.inventory_service.dto.request.CurrencyRequestDTO;
import com.arka.inventory_service.dto.response.CurrencyResponseDTO;
import com.arka.inventory_service.exception.ResourceAlreadyExistsException;
import com.arka.inventory_service.exception.ResourceNotFoundException;
import com.arka.inventory_service.mapper.EntityToDTOMapper;
import com.arka.inventory_service.model.Currency;
import com.arka.inventory_service.repository.CurrencyRepository;
import com.arka.inventory_service.service.ICurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements ICurrencyService {

    private final CurrencyRepository currencyRepository;
    private final EntityToDTOMapper mapper;

    @Override
    public CurrencyResponseDTO createCurrency(CurrencyRequestDTO request) {
        return mapper.toDTO(createEntity(request));
    }

    @Override
    public CurrencyResponseDTO getCurrencyById(UUID id) {
        return mapper.toDTO(getCurrencyByIdOrException(id));
    }

    @Override
    public CurrencyResponseDTO getCurrencyByName(String name) {
        Currency currency = currencyRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new ResourceNotFoundException("Currency not found."));
        return mapper.toDTO(currency);
    }

    @Override
    public List<CurrencyResponseDTO> getCurrenciesBySymbol(String symbol) {
        return createResponseList(currencyRepository.findAllBySymbol(symbol));
    }

    @Override
    public CurrencyResponseDTO getCurrencyByCode(String code) {
        Currency currency = currencyRepository.findByCodeIgnoreCase(code)
                .orElseThrow(() -> new ResourceNotFoundException("Currency not found."));
        return mapper.toDTO(currency);
    }

    @Override
    public List<CurrencyResponseDTO> getAllCurrencies() {
        return createResponseList(currencyRepository.findAll());
    }

    // --- Private utilitarian methods ---

    private Currency createEntity(CurrencyRequestDTO request) {
        validateUnique(request.getName(), request.getCode());

        Currency currency = new Currency();
        currency.setName(request.getName());
        currency.setCode(request.getCode());
        currency.setSymbol(request.getSymbol());

        return currencyRepository.save(currency);
    }

    private Currency getCurrencyByIdOrException(UUID id) {
        return currencyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Currency not found."));
    }

    private void validateUnique(String name, String code) {
        boolean existsByName = currencyRepository.existsByNameIgnoreCase(name);
        boolean existsByCode = currencyRepository.existsByCodeIgnoreCase(code);

        if (existsByName) throw new ResourceAlreadyExistsException("Currency name already exists.");
        if (existsByCode) throw new ResourceAlreadyExistsException("Currency code already exists.");
    }

    private List<CurrencyResponseDTO> createResponseList(List<Currency> currencies) {
        return currencies.stream().map(mapper::toDTO).toList();
    }
}
