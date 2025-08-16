package com.arka.inventory_service.service;

import com.arka.inventory_service.dto.request.StockRequestDTO;
import com.arka.inventory_service.dto.request.StockUpdateByReservationRequestDTO;
import com.arka.inventory_service.dto.request.StockUpdateByWithdrawRequestDTO;
import com.arka.inventory_service.dto.request.StockUpdateRequestDTO;
import com.arka.inventory_service.dto.response.StockByCheckoutResponseDTO;
import com.arka.inventory_service.dto.response.StockCartValidationResponseDTO;
import com.arka.inventory_service.dto.response.StockResponseDTO;

import java.util.List;
import java.util.UUID;

public interface IStockService {
    StockResponseDTO createStock(StockRequestDTO request);
    StockResponseDTO getStockById(UUID id);
    List<StockResponseDTO> getStockByProductVariantId(UUID variantId);
    List<StockResponseDTO> getStockByWarehouseId(UUID warehouseId);
    List<StockResponseDTO> getAllStocks();
    StockResponseDTO updateStock(UUID id, StockUpdateRequestDTO request);
    List<StockCartValidationResponseDTO> validateCartStock(UUID cartId);
    void updateStockByWithdraw(StockUpdateByWithdrawRequestDTO request);
    void updateStockByReservation(StockUpdateByReservationRequestDTO request);
    void updateStockByExpiredReservation();
    List<StockByCheckoutResponseDTO> finalizeStockReservationByCheckout(UUID cartId);
}
