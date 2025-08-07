package com.arka.inventory_service.service;

import com.arka.inventory_service.dto.request.StockRequestDTO;
import com.arka.inventory_service.dto.response.StockResponseDTO;

import java.util.List;
import java.util.UUID;

public interface IStockService {
    StockResponseDTO createStock(StockRequestDTO request);
    StockResponseDTO getStockById(UUID id);
    List<StockResponseDTO> getStockByProductVariantId(UUID variantId);
    List<StockResponseDTO> getStockByWarehouseId(UUID warehouseId);
    List<StockResponseDTO> getAllStocks();
}
