package com.arka.inventory_service.service;

import com.arka.inventory_service.dto.request.StockThresholdRequestDTO;
import com.arka.inventory_service.dto.response.StockThresholdResponseDTO;

import java.util.List;
import java.util.UUID;

public interface IStockThresholdService {
    StockThresholdResponseDTO createStockThreshold(StockThresholdRequestDTO request);
    StockThresholdResponseDTO getStockThresholdById(UUID id);
    List<StockThresholdResponseDTO> getStockThresholdsByMinimumQuantity(Integer minimumQuantity);
    List<StockThresholdResponseDTO> getAllStockThresholds();
    StockThresholdResponseDTO updateStockThreshold(StockThresholdRequestDTO request);
}
