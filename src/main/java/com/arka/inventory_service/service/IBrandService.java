package com.arka.inventory_service.service;

import com.arka.inventory_service.dto.request.BrandRequestDTO;
import com.arka.inventory_service.dto.response.BrandResponseDTO;

import java.util.List;
import java.util.UUID;

public interface IBrandService {
    BrandResponseDTO createBrand(BrandRequestDTO request);
    BrandResponseDTO getBrandById(UUID id);
    BrandResponseDTO getBrandByName(String name);
    List<BrandResponseDTO> getAllBrands();
    BrandResponseDTO updateBrand(UUID id, BrandRequestDTO request);
}
