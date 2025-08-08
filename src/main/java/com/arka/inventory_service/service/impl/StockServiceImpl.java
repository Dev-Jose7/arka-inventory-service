package com.arka.inventory_service.service.impl;

import com.arka.inventory_service.dto.request.StockRequestDTO;
import com.arka.inventory_service.dto.response.StockResponseDTO;
import com.arka.inventory_service.exception.ResourceAlreadyExistsException;
import com.arka.inventory_service.exception.ResourceNotFoundException;
import com.arka.inventory_service.mapper.EntityToDTOMapper;
import com.arka.inventory_service.model.ProductVariant;
import com.arka.inventory_service.model.Stock;
import com.arka.inventory_service.model.Warehouse;
import com.arka.inventory_service.notification.NotificationService;
import com.arka.inventory_service.repository.ProductVariantRepository;
import com.arka.inventory_service.repository.StockRepository;
import com.arka.inventory_service.repository.WarehouseRepository;
import com.arka.inventory_service.service.IStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements IStockService {

    private final StockRepository stockRepository;
    private final ProductVariantRepository productVariantRepository;
    private final WarehouseRepository warehouseRepository;
    private final EntityToDTOMapper mapper;
    private final NotificationService notificationService;

    @Override
    public StockResponseDTO createStock(StockRequestDTO request) {
        Stock stock = stockRepository.save(createEntity(request));
        notificationService.notifyStockEntry(stock);
        return mapper.toDTO(stock);
    }

    @Override
    public StockResponseDTO getStockById(UUID id) {
        return mapper.toDTO(getStockByIdOrException(id));
    }

    @Override
    public List<StockResponseDTO> getStockByProductVariantId(UUID variantId) {
        return createResponseList(stockRepository.findAllByProductVariantId(variantId));
    }

    @Override
    public List<StockResponseDTO> getStockByWarehouseId(UUID warehouseId) {
        return createResponseList(stockRepository.findAllByWarehouseId(warehouseId));
    }

    @Override
    public List<StockResponseDTO> getAllStocks() {
        return createResponseList(stockRepository.findAll());
    }

    // --- Private utilitarian methods ---

    private Stock createEntity(StockRequestDTO request) {
        validateUnique(request.getProductVariantId(), request.getWarehouseId());

        ProductVariant variant = getProductVariantByIdOrException(request.getProductVariantId());
        Warehouse warehouse = getWarehouseByIdOrException(request.getWarehouseId());

        Stock stock = new Stock();
        stock.setProductVariant(variant);
        stock.setWarehouse(warehouse);
        stock.setStock(request.getStock());

        return stock;
    }

    private Stock getStockByIdOrException(UUID id) {
        return stockRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Stock not found."));
    }

    private ProductVariant getProductVariantByIdOrException(UUID id) {
        return productVariantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product variant not found."));
    }

    private Warehouse getWarehouseByIdOrException(UUID id) {
        return warehouseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found."));
    }

    private void validateUnique(UUID variantId, UUID warehouseId) {
        boolean exists = stockRepository.existsByProductVariantIdAndWarehouseId(variantId, warehouseId);
        if (exists)
            throw new ResourceAlreadyExistsException("Stock already exists for this variant in the selected warehouse.");
    }

    private List<StockResponseDTO> createResponseList(List<Stock> stockList) {
        return stockList.stream()
                .map(mapper::toDTO)
                .toList();
    }
}
