package com.arka.inventory_service.service.impl;

import com.arka.inventory_service.dto.request.ProductVariantRequestDTO;
import com.arka.inventory_service.dto.response.ProductVariantResponseDTO;
import com.arka.inventory_service.exception.ResourceAlreadyExistsException;
import com.arka.inventory_service.exception.ResourceNotFoundException;
import com.arka.inventory_service.mapper.EntityToDTOMapper;
import com.arka.inventory_service.model.Currency;
import com.arka.inventory_service.model.Product;
import com.arka.inventory_service.model.ProductVariant;
import com.arka.inventory_service.notification.NotificationService;
import com.arka.inventory_service.repository.CurrencyRepository;
import com.arka.inventory_service.repository.ProductRepository;
import com.arka.inventory_service.repository.ProductVariantRepository;
import com.arka.inventory_service.service.IProductVariantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductVariantServiceImpl implements IProductVariantService {

    private final ProductVariantRepository productVariantRepository;
    private final ProductRepository productRepository;
    private final CurrencyRepository currencyRepository;
    private final EntityToDTOMapper mapper;
    private final NotificationService notificationService;

    @Override
    public ProductVariantResponseDTO createProductVariant(ProductVariantRequestDTO request) {
        ProductVariant productVariant = productVariantRepository.save(createEntity(request));
        notificationService.notifyNewVariant(productVariant);
        return mapper.toDTO(productVariant);
    }

    @Override
    public List<ProductVariantResponseDTO> getAllProducts() {
        return createResponseList(productVariantRepository.findAll());
    }

    @Override
    public ProductVariantResponseDTO getProductVariantBySku(String sku) {
        ProductVariant productVariant = productVariantRepository.findBySku(sku)
                .orElseThrow(() -> new ResourceNotFoundException("Product variant not found."));

        return mapper.toDTO(productVariant);
    }

    @Override
    public List<ProductVariantResponseDTO> getProductsVariantByName(String name) {
        return createResponseList(productVariantRepository.findAllByNameIgnoreCase(name));
    }

    @Override
    public List<ProductVariantResponseDTO> getProductsVariantByProductId(UUID productId) {
        return createResponseList(productVariantRepository.findAllByProductId(productId));
    }

    @Override
    public List<ProductVariantResponseDTO> getProductsVariantByCurrencyId(UUID currencyId) {
        return createResponseList(productVariantRepository.findAllByCurrencyId(currencyId));
    }

    @Override
    public List<ProductVariantResponseDTO> getProductsVariantByPrice(BigDecimal price) {
        return createResponseList(productVariantRepository.findAllByPrice(price));
    }

    private ProductVariant getProductVariantByIdOrException(UUID id){
        return productVariantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product variant not found."));
    }

    private Product getProductByIdOrException(UUID id){
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found."));
    }

    private Currency getCurrencyByIdOrException(UUID id) {
        return currencyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Currency not found."));
    }

    private void validateUnique(String name, UUID productId){
        boolean check = productVariantRepository.existsByNameIgnoreCaseAndProductId(name, productId);

        if (check) throw new ResourceAlreadyExistsException("Product variant name already exists with that product.");
    }

    private String generateSku(Product product) {
        String categoryPrefix = product.getCategory().getName()
                .replaceAll("[^A-Za-z]", "")
                .substring(0, Math.min(3, product.getCategory().getName().length()))
                .toUpperCase();

        String brandPrefix = product.getBrand().getName()
                .replaceAll("[^A-Za-z]", "")
                .substring(0, Math.min(3, product.getBrand().getName().length()))
                .toUpperCase();

        long count = productVariantRepository.countByProductId(product.getId());

        String sequenceNumber = String.format("%03d", count + 1);

        return categoryPrefix + "-" + brandPrefix + "-" + sequenceNumber;
    }

    private ProductVariant createEntity(ProductVariantRequestDTO request) {
        validateUnique(request.getName(), request.getProductId());
        Product product = getProductByIdOrException(request.getProductId());
        ProductVariant variant = new ProductVariant();

        variant.setSku(generateSku(product));
        variant.setName(request.getName());
        variant.setProduct(product);
        variant.setCurrency(getCurrencyByIdOrException(request.getCurrencyId()));
        variant.setPrice(request.getPrice());

        return variant;
    }

    private List<ProductVariantResponseDTO> createResponseList(List<ProductVariant> variants){
        return variants.stream().map(mapper::toDTO).toList();
    }


}

