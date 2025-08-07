package com.arka.inventory_service.service;

import com.arka.inventory_service.dto.request.ProductVariantRequestDTO;
import com.arka.inventory_service.dto.response.ProductVariantResponseDTO;
import com.arka.inventory_service.model.ProductVariant;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface IProductVariantService {
    ProductVariantResponseDTO createProductVariant(ProductVariantRequestDTO request);
    List<ProductVariantResponseDTO> getAllProducts();
    ProductVariantResponseDTO getProductVariantBySku(String sku);
    List<ProductVariantResponseDTO> getProductsVariantByName(String name);
    List<ProductVariantResponseDTO> getProductsVariantByProductId(UUID productId);
    List<ProductVariantResponseDTO> getProductsVariantByCurrencyId(UUID currencyId);
    List<ProductVariantResponseDTO> getProductsVariantByPrice(BigDecimal price);
}
