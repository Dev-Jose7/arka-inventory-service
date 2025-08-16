package com.arka.inventory_service.service.impl;

import com.arka.inventory_service.dto.request.WarehouseRequestDTO;
import com.arka.inventory_service.dto.request.WarehouseUpdateRequestDTO;
import com.arka.inventory_service.dto.response.WarehouseResponseDTO;
import com.arka.inventory_service.exception.ResourceAlreadyExistsException;
import com.arka.inventory_service.exception.ResourceNotFoundException;
import com.arka.inventory_service.mapper.EntityToDTOMapper;
import com.arka.inventory_service.model.Country;
import com.arka.inventory_service.model.Warehouse;
import com.arka.inventory_service.repository.CountryRepository;
import com.arka.inventory_service.repository.WarehouseRepository;
import com.arka.inventory_service.service.IWarehouseService;
import com.arka.inventory_service.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements IWarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final CountryRepository countryRepository;
    private final EntityToDTOMapper mapper;

    @Override
    public WarehouseResponseDTO createWarehouse(WarehouseRequestDTO request) {
        return mapper.toDTO(createEntity(request));
    }

    @Override
    public WarehouseResponseDTO getWarehouseById(UUID id) {
        return mapper.toDTO(getWarehouseByIdOrException(id));
    }

    @Override
    public WarehouseResponseDTO getWarehouseByName(String name) {
        Warehouse warehouse = warehouseRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found."));
        return mapper.toDTO(warehouse);
    }

    @Override
    public List<WarehouseResponseDTO> getWarehouseByLocation(String location) {
        return createResponseList(warehouseRepository.findAllByLocationIgnoreCase(location));
    }

    @Override
    public List<WarehouseResponseDTO> getWarehouseByCountry(UUID id) {
        return createResponseList(warehouseRepository.findAllByCountryId(id));
    }

    @Override
    public List<WarehouseResponseDTO> getAllWarehouses() {
        return createResponseList(warehouseRepository.findAll());
    }

    @Override
    public WarehouseResponseDTO updateWarehouse(UUID id, WarehouseUpdateRequestDTO request) {
        return mapper.toDTO(updateEntity(id, request));
    }

    // --- Private utility methods ---

    private Warehouse createEntity(WarehouseRequestDTO request) {
        validateUnique(request.getName());

        Country country = getCountryByIdOrException(request.getCountryId());

        Warehouse warehouse = new Warehouse();
        warehouse.setName(request.getName());
        warehouse.setLocation(request.getLocation());
        warehouse.setCountry(country);

        return warehouseRepository.save(warehouse);
    }

    private Warehouse updateEntity(UUID id, WarehouseUpdateRequestDTO request) {
        Warehouse warehouse = getWarehouseByIdOrException(id);

        if (ValidationUtil.isValid(request.getName(), warehouse.getName())) {
            validateUnique(request.getName());
            warehouse.setName(request.getName());
        }

        if (ValidationUtil.isValid(request.getLocation(), warehouse.getLocation())) warehouse.setLocation(request.getLocation());

        if (ValidationUtil.isValid(request.getCountryId(), warehouse.getCountry().getId())) {
            Country country = getCountryByIdOrException(request.getCountryId());
            warehouse.setCountry(country);
        }

        return warehouseRepository.save(warehouse);
    }

    private Warehouse getWarehouseByIdOrException(UUID id) {
        return warehouseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found."));
    }

    private Country getCountryByIdOrException(UUID id) {
        return countryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Country not found."));
    }

    private void validateUnique(String name) {
        boolean exists = warehouseRepository.existsByNameIgnoreCase(name);

        if (exists) throw new ResourceAlreadyExistsException("Warehouse name already exists.");
    }

    private List<WarehouseResponseDTO> createResponseList(List<Warehouse> warehouses) {
        return warehouses.stream().map(mapper::toDTO).toList();
    }
}
