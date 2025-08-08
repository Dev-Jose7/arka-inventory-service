package com.arka.inventory_service.service.impl;

import com.arka.inventory_service.dto.request.CountryRequestDTO;
import com.arka.inventory_service.dto.response.CountryResponseDTO;
import com.arka.inventory_service.exception.ResourceAlreadyExistsException;
import com.arka.inventory_service.exception.ResourceNotFoundException;
import com.arka.inventory_service.mapper.EntityToDTOMapper;
import com.arka.inventory_service.model.Country;
import com.arka.inventory_service.repository.CountryRepository;
import com.arka.inventory_service.service.ICountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements ICountryService {

    private final CountryRepository countryRepository;
    private final EntityToDTOMapper mapper;

    @Override
    public CountryResponseDTO createCountry(CountryRequestDTO request) {
        return mapper.toDTO(createEntity(request));
    }

    @Override
    public CountryResponseDTO getCountryById(UUID id) {
        return mapper.toDTO(getCountryByIdOrException(id));
    }

    @Override
    public CountryResponseDTO getCountryByName(String name) {
        Country country = countryRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new ResourceNotFoundException("Country not found."));
        return mapper.toDTO(country);
    }

    @Override
    public List<CountryResponseDTO> getAllCountries() {
        return createResponseList(countryRepository.findAll());
    }

    // --- Private utilitarian methods ---

    private Country createEntity(CountryRequestDTO request) {
        validateUnique(request.getName());

        Country country = new Country();
        country.setName(request.getName());

        return countryRepository.save(country);
    }

    private Country getCountryByIdOrException(UUID id) {
        return countryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Country not found."));
    }

    private void validateUnique(String name) {
        boolean exists = countryRepository.existsByNameIgnoreCase(name);

        if (exists) throw new ResourceAlreadyExistsException("Country name already exists.");
    }

    private List<CountryResponseDTO> createResponseList(List<Country> countries) {
        return countries.stream().map(mapper::toDTO).toList();
    }
}
