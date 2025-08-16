package com.arka.inventory_service.service;

import com.arka.inventory_service.dto.request.CategoryRequestDTO;
import com.arka.inventory_service.dto.request.CategoryUpdateRequestDTO;
import com.arka.inventory_service.dto.response.CategoryResponseDTO;

import java.util.List;
import java.util.UUID;

public interface ICategoryService {
    CategoryResponseDTO createCategory(CategoryRequestDTO request);
    CategoryResponseDTO getCategoryById(UUID id);
    CategoryResponseDTO getCategoryByName(String name);
    List<CategoryResponseDTO> getAllCategories();
    CategoryResponseDTO updateCategory(UUID id, CategoryUpdateRequestDTO request);
}
