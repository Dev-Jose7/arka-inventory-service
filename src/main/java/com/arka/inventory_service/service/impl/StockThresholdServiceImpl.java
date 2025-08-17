package com.arka.inventory_service.service.impl;

import com.arka.inventory_service.dto.request.StockThresholdRequestDTO;
import com.arka.inventory_service.dto.response.StockThresholdResponseDTO;
import com.arka.inventory_service.exception.ResourceAlreadyExistsException;
import com.arka.inventory_service.exception.ResourceNotFoundException;
import com.arka.inventory_service.mapper.EntityToDTOMapper;
import com.arka.inventory_service.model.Stock;
import com.arka.inventory_service.model.StockThreshold;
import com.arka.inventory_service.repository.StockRepository;
import com.arka.inventory_service.repository.StockThresholdRepository;
import com.arka.inventory_service.service.IStockThresholdService;
import com.arka.inventory_service.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StockThresholdServiceImpl implements IStockThresholdService {

    private final StockThresholdRepository stockThresholdRepository;
    private final StockRepository stockRepository;
    private final EntityToDTOMapper mapper;

    @Override
    public StockThresholdResponseDTO createStockThreshold(StockThresholdRequestDTO request) {
        return mapper.toDTO(createEntity(request));
    }

    @Override
    public StockThresholdResponseDTO getStockThresholdById(UUID id) {
        return mapper.toDTO(getStockThresholdByIdOrException(id));
    }

    @Override
    public List<StockThresholdResponseDTO> getStockThresholdsByMinimumQuantity(Integer minimumQuantity) {
        return createResponseList(stockThresholdRepository.findAllByMinimumQuantityLessThanEqual(minimumQuantity));
    }

    @Override
    public List<StockThresholdResponseDTO> getAllStockThresholds() {
        return createResponseList(stockThresholdRepository.findAll());
    }

    @Override
    public StockThresholdResponseDTO updateStockThreshold(StockThresholdRequestDTO request) {
        return mapper.toDTO(updateEntity(request));
    }

    public StockThreshold getStockThresholdByIdOrException(UUID id) {
        return stockThresholdRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Stock threshold not found."));
    }

    public Stock getStockByIdOrException(UUID id) {
        return stockRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Stock not found."));
    }

    public void validateUnique(UUID id) {
        boolean status = stockThresholdRepository.existsById(id);

        if (status) throw new ResourceAlreadyExistsException("StockThreshold already exists.");
    }

    public StockThreshold createEntity(StockThresholdRequestDTO request) {
        validateUnique(request.getStockId());

        StockThreshold stockThreshold = new StockThreshold();
        stockThreshold.setStock(getStockByIdOrException(request.getStockId()));
        stockThreshold.setMinimumQuantity(request.getMinimumQuantity());

        return stockThresholdRepository.save(stockThreshold);
    }

    public StockThreshold updateEntity(StockThresholdRequestDTO request) {
        StockThreshold stockThreshold = getStockThresholdByIdOrException(request.getStockId());

        if (ValidationUtil.isValid(request.getMinimumQuantity(), stockThreshold.getMinimumQuantity())) {
            stockThreshold.setMinimumQuantity(request.getMinimumQuantity());
        }

        return stockThresholdRepository.save(stockThreshold);
    }

    public List<StockThresholdResponseDTO> createResponseList(List<StockThreshold> stockThresholds) {
        return stockThresholds.stream().map(mapper::toDTO).toList();
    }
}
