package com.arka.inventory_service.service.impl;

import com.arka.inventory_service.dto.request.StockRequestDTO;
import com.arka.inventory_service.dto.request.StockUpdateByReservationRequestDTO;
import com.arka.inventory_service.dto.request.StockUpdateByWithdrawRequestDTO;
import com.arka.inventory_service.dto.request.StockUpdateRequestDTO;
import com.arka.inventory_service.dto.response.StockByCheckoutResponseDTO;
import com.arka.inventory_service.dto.response.StockCartValidationResponseDTO;
import com.arka.inventory_service.dto.response.StockResponseDTO;
import com.arka.inventory_service.enums.ReservationStatus;
import com.arka.inventory_service.enums.StockMovementType;
import com.arka.inventory_service.exception.ResourceAlreadyExistsException;
import com.arka.inventory_service.exception.ResourceNotFoundException;
import com.arka.inventory_service.exception.StockNotAvailableException;
import com.arka.inventory_service.mapper.EntityToDTOMapper;
import com.arka.inventory_service.model.*;
import com.arka.inventory_service.notification.NotificationService;
import com.arka.inventory_service.repository.*;
import com.arka.inventory_service.service.IStockMovementService;
import com.arka.inventory_service.service.IStockService;
import com.arka.inventory_service.util.ValidationUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements IStockService {

    private final StockRepository stockRepository;
    private final ProductVariantRepository productVariantRepository;
    private final WarehouseRepository warehouseRepository;
    private final StockReservationRepository stockReservationRepository;
    private final EntityToDTOMapper mapper;
    private final NotificationService notificationService;
    private final IStockMovementService stockMovementService;

    @Override
    public StockResponseDTO createStock(StockRequestDTO request) {
        Stock stock = stockRepository.save(createEntity(request));

        stockMovementService.createStockMovement(
                stock.getId(),
                StockMovementType.IN,
                request.getStock(),
                "Initial stock created for " +
                        stock.getProductVariant().getProduct().getName() + " " +
                        stock.getProductVariant().getName() + ": " + request.getStock() + " units"
        );

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

    @Override
    public List<StockCartValidationResponseDTO> validateCartStock(UUID cartId) {
        LocalDateTime threshold = LocalDateTime.now().minusDays(1);
        List<StockReservation> recentReservations = stockReservationRepository
                .findAllByCartIdAndCreatedAtAfter(cartId, threshold);

        List<StockCartValidationResponseDTO> result = new ArrayList<>();

        for (StockReservation reservation : recentReservations) {
            Stock stock = reservation.getStockMovement().getStock();
            int requestedQty = reservation.getStockMovement().getQuantity();
            int availableQty = stock.getUnit();

            boolean isAvailable = availableQty >= requestedQty;

            result.add(new StockCartValidationResponseDTO(
                    stock.getId(),
                    stock.getProductVariant().getId(),
                    stock.getProductVariant().getProduct().getName(),
                    stock.getProductVariant().getName(),
                    requestedQty,
                    availableQty,
                    isAvailable
            ));
        }

        return result;
    }


    @Override
    public StockResponseDTO updateStock(UUID id, StockUpdateRequestDTO request) {
        return mapper.toDTO(updateEntity(id, request));
    }

    @Override
    public void updateStockByWithdraw(StockUpdateByWithdrawRequestDTO request) {
        Stock stock = getStockByIdOrException(request.getId());

        if (ValidationUtil.isGreaterThan(request.getQuantity(), stock.getUnit())) {
            throw new StockNotAvailableException("Not enough stock to withdraw");
        }

        stock.setUnit(stock.getUnit() - request.getQuantity());

        stockMovementService.createStockMovement(
                stock.getId(),
                StockMovementType.OUT,
                request.getQuantity(),
                "Stock withdrawn: " + request.getQuantity() + " unit(s) of product '" +
                        stock.getProductVariant().getProduct().getName() + " - " +
                        stock.getProductVariant().getName() +
                        "' due to " + request.getReason().toLowerCase() + "."
        );

        stockRepository.save(stock);
    }

    @Transactional
    @Override
    public void updateStockByReservation(StockUpdateByReservationRequestDTO request) {
        List<Stock> stockList = stockRepository.findAllByProductVariantId(request.getProductVariantId());
        List<Stock> stockSelected = chooseStock(stockList, request.getQuantity());

        if (ValidationUtil.isGreaterThan(stockSelected.size(), 1)) {
            Integer countStock = request.getQuantity(); // Get reserved quantity do count decreased.

            for (Stock stock : stockSelected){
                int reservedQty; // To save unit of stock and register in stock movement

                if (countStock >= stock.getUnit()) {
                    reservedQty = stock.getUnit();
                    stock.setUnit(0);
                } else {
                    reservedQty = countStock;
                    stock.setUnit(stock.getUnit() - reservedQty);
                }

                countStock -= reservedQty;

                stockRepository.save(stock);

                stockMovementService.createStockMovementWithReservation(
                        stock.getId(),
                        request.getCartId(),
                        StockMovementType.RESERVED,
                        reservedQty,
                        "Reserved " + reservedQty + " unit(s) of product '" +
                                stock.getProductVariant().getProduct().getName() + " - " +
                                stock.getProductVariant().getName() +
                                "' for cart ID: " + request.getCartId()
                );
            }


        } else {
            Stock stock = stockSelected.getFirst();
            int delta = stock.getUnit() - request.getQuantity();

            stock.setUnit(delta);
            stockRepository.save(stock);

            stockMovementService.createStockMovementWithReservation(
                    stock.getId(),
                    request.getCartId(),
                    StockMovementType.RESERVED,
                    request.getQuantity(),
                    "Reserved " + request.getQuantity() + " unit(s) of product '" +
                            stock.getProductVariant().getProduct().getName() + " - " +
                            stock.getProductVariant().getName() +
                            "' for cart ID: " + request.getCartId()
            );
        }
    }

    @Transactional
    @Override
    public void updateStockByExpiredReservation() {
        List<StockReservation> stockReservationList = stockReservationRepository.findAllByStatusAndExpiresAtBefore(ReservationStatus.ACTIVE, LocalDateTime.now());

        for (StockReservation stockReservation : stockReservationList) {
            Stock stock = stockReservation.getStockMovement().getStock();
            stock.setUnit(stock.getUnit() + stockReservation.getStockMovement().getQuantity());
            stockReservation.setStatus(ReservationStatus.EXPIRED);

            stockMovementService.createStockMovement(
                    stockReservation.getStockMovement().getStock().getId(),
                    StockMovementType.RELEASED,
                    stockReservation.getStockMovement().getQuantity(),
                    "Released " + stockReservation.getStockMovement().getQuantity() + " unit(s) of product '" +
                            stockReservation.getStockMovement().getStock().getProductVariant().getProduct().getName() + " - " +
                            stockReservation.getStockMovement().getStock().getProductVariant().getName() +
                            "' due to expired reservation (cart ID: " +
                            stockReservation.getCartId() + ")."
            );

            stockRepository.save(stock);
            stockReservationRepository.save(stockReservation);
        }
    }

    @Transactional
    @Override
    public List<StockByCheckoutResponseDTO> finalizeStockReservationByCheckout(UUID cartId) {
        List<StockByCheckoutResponseDTO> stockCheckoutList = new ArrayList<>();

        List<StockReservation> activeReservations = stockReservationRepository.findAllByStatusAndCartId(ReservationStatus.ACTIVE, cartId);
        if (!activeReservations.isEmpty()) {
            for (StockReservation reservation : activeReservations) {
                processActiveReservation(reservation, stockCheckoutList);
            }
        } else {
            List<StockReservation> expiredReservations = stockReservationRepository.findAllByStatusAndCartId(ReservationStatus.EXPIRED, cartId);
            for (StockReservation reservation : expiredReservations) {
                processExpiredReservation(reservation, stockCheckoutList);
            }
        }

        return stockCheckoutList;
    }

    // --- Private methods ---

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
        if (exists) {
            throw new ResourceAlreadyExistsException("Stock already exists for this variant in the selected warehouse.");
        }
    }

    private List<Stock> chooseStock(List<Stock> stocksProductVariant, Integer quantity) {
        List<Stock> stockAvailable = new ArrayList<>();
        Integer counterStock = 0;

        for (Stock stock : stocksProductVariant) {
            if (ValidationUtil.isGreaterThan(stock.getUnit(), quantity)) {
                return List.of(stock);
            } else if (ValidationUtil.isGreaterThan(stock.getUnit(), 0)) {
                stockAvailable.add(stock);
                counterStock += stock.getUnit();

                if (counterStock >= quantity) return stockAvailable;
            }
        }

        throw new StockNotAvailableException();
    }

    private Stock createEntity(StockRequestDTO request) {
        validateUnique(request.getProductVariantId(), request.getWarehouseId());

        ProductVariant variant = getProductVariantByIdOrException(request.getProductVariantId());
        Warehouse warehouse = getWarehouseByIdOrException(request.getWarehouseId());

        Stock stock = new Stock();
        stock.setProductVariant(variant);
        stock.setWarehouse(warehouse);
        stock.setUnit(request.getStock());

        return stock;
    }

    private Stock updateEntity(UUID id, StockUpdateRequestDTO request) {
        Stock stock = getStockByIdOrException(id);

        updateProductVariant(stock, request.getProductVariantId());
        updateWarehouse(stock, request.getWarehouseId());
        updateStockQuantity(stock, request.getStock());

        return stockRepository.save(stock);
    }

    private void updateProductVariant(Stock stock, UUID newVariantId) {
        if (ValidationUtil.isValid(newVariantId, stock.getProductVariant().getId())) {
            ProductVariant variant = getProductVariantByIdOrException(newVariantId);
            if (stockRepository.existsByProductVariantIdAndWarehouseId(variant.getId(), stock.getWarehouse().getId())) {
                throw new ResourceAlreadyExistsException("Stock already exists for this variant in the selected warehouse.");
            }
            stock.setProductVariant(variant);
        }
    }

    private void updateWarehouse(Stock stock, UUID newWarehouseId) {
        if (ValidationUtil.isValid(newWarehouseId, stock.getWarehouse().getId())) {
            Warehouse warehouse = getWarehouseByIdOrException(newWarehouseId);
            if (stockRepository.existsByProductVariantIdAndWarehouseId(stock.getProductVariant().getId(), warehouse.getId())) {
                throw new ResourceAlreadyExistsException("Stock already exists for this variant in the selected warehouse.");
            }
            stock.setWarehouse(warehouse);

            stockMovementService.createStockMovement(
                    stock.getId(),
                    StockMovementType.TRANSFER,
                    stock.getUnit(),
                    "Stock transferred to warehouse '" + warehouse.getName() + "' for product: " +
                            stock.getProductVariant().getProduct().getName() + " " +
                            stock.getProductVariant().getName() + " (" + stock.getUnit() + " units)"
            );
        }
    }

    private void updateStockQuantity(Stock stock, Integer newQuantity) {
        if (ValidationUtil.isValid(newQuantity, stock.getUnit())) {
            int previousStock = stock.getUnit();
            int delta = newQuantity - previousStock;

            stock.setUnit(newQuantity);

            if (delta != 0) {
                StockMovementType type = delta > 0 ? StockMovementType.IN : StockMovementType.ADJUST;

                stockMovementService.createStockMovement(
                        stock.getId(),
                        type,
                        Math.abs(delta),
                        "Stock " + (delta > 0 ? "increased" : "adjusted down") + " for " +
                                stock.getProductVariant().getProduct().getName() + " " +
                                stock.getProductVariant().getName() + ": " + Math.abs(delta) + " units"
                );
            }
        }
    }

    private void processActiveReservation(StockReservation reservation, List<StockByCheckoutResponseDTO> list) {
        Stock stock = reservation.getStockMovement().getStock();
        reservation.setStatus(ReservationStatus.COMPLETED);

        stockMovementService.createStockMovement(
                stock.getId(),
                StockMovementType.OUT,
                reservation.getStockMovement().getQuantity(),
                "Checked out " + reservation.getStockMovement().getQuantity() + " unit(s) of product '" +
                        stock.getProductVariant().getProduct().getName() + " - " + stock.getProductVariant().getName() +
                        "' due to completed purchase (cart ID: " + reservation.getCartId() + ")."
        );

        list.add(mapper.toDTO(stock, reservation));
        stockReservationRepository.save(reservation);
    }

    private void processExpiredReservation(StockReservation reservation, List<StockByCheckoutResponseDTO> list) {
        Stock stock = reservation.getStockMovement().getStock();
        int quantity = reservation.getStockMovement().getQuantity();

        if (stock.getUnit() >= quantity) {
            int newQuantity = stock.getUnit() - quantity;
            updateStockQuantity(stock, newQuantity);

            list.add(mapper.toDTO(stock, reservation));

            stockMovementService.createStockMovement(
                    stock.getId(),
                    StockMovementType.OUT,
                    quantity,
                    "Checked out " + quantity + " unit(s) of product '" +
                            stock.getProductVariant().getProduct().getName() + " - " + stock.getProductVariant().getName() +
                            "' due to completed purchase (cart ID: " + reservation.getCartId() + ")."
            );
        }
    }

    private List<StockResponseDTO> createResponseList(List<Stock> stockList) {
        return stockList.stream()
                .map(mapper::toDTO)
                .toList();
    }
}
