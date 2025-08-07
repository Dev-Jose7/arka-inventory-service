package com.arka.inventory_service.service;

import com.arka.inventory_service.dto.request.WarehouseRequestDTO;
import com.arka.inventory_service.dto.response.WarehouseResponseDTO;

import java.util.List;
import java.util.UUID;

public interface IWarehouseService {
    WarehouseResponseDTO createWarehouse(WarehouseRequestDTO request);
    WarehouseResponseDTO getWarehouseById(UUID id);
    WarehouseResponseDTO getWarehouseByName(String name);
    List<WarehouseResponseDTO> getWarehouseByLocation(String location);
    List<WarehouseResponseDTO> getWarehouseByCountry(UUID id);
    List<WarehouseResponseDTO> getAllWarehouses();
}
