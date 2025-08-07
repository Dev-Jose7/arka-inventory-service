package com.arka.inventory_service.service;

import com.arka.inventory_service.dto.request.ProductRequestDTO;
import com.arka.inventory_service.dto.response.ProductResponseDTO;

import java.util.List;
import java.util.UUID;

public interface IProductService {
    ProductResponseDTO createProduct(ProductRequestDTO request);
    List<ProductResponseDTO> getAllProducts();
    List<ProductResponseDTO> getProductsByName(String name);
    List<ProductResponseDTO> getProductByBrandId(UUID brandId);
    List<ProductResponseDTO> getProductByCategoryId(UUID categoryId);
}
