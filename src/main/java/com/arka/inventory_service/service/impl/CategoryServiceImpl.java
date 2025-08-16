package com.arka.inventory_service.service.impl;

import com.arka.inventory_service.dto.request.CategoryRequestDTO;
import com.arka.inventory_service.dto.request.CategoryUpdateRequestDTO;
import com.arka.inventory_service.dto.response.CategoryResponseDTO;
import com.arka.inventory_service.exception.ResourceAlreadyExistsException;
import com.arka.inventory_service.exception.ResourceNotFoundException;
import com.arka.inventory_service.mapper.EntityToDTOMapper;
import com.arka.inventory_service.model.Category;
import com.arka.inventory_service.repository.CategoryRepository;
import com.arka.inventory_service.service.ICategoryService;
import com.arka.inventory_service.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements ICategoryService {

    private final CategoryRepository categoryRepository;
    private final EntityToDTOMapper mapper;

    @Override
    public CategoryResponseDTO createCategory(CategoryRequestDTO request) {
        return mapper.toDTO(createEntity(request));
    }

    @Override
    public CategoryResponseDTO getCategoryById(UUID id) {
        return mapper.toDTO(getCategoryByIdOrException(id));
    }

    @Override
    public CategoryResponseDTO getCategoryByName(String name) {
        Category category = categoryRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found."));
        return mapper.toDTO(category);
    }

    @Override
    public List<CategoryResponseDTO> getAllCategories() {
        return createResponseList(categoryRepository.findAll());
    }

    @Override
    public CategoryResponseDTO updateCategory(UUID id, CategoryUpdateRequestDTO request) {
        return mapper.toDTO(updateEntity(id, request));
    }

    // --- Private utility methods ---

    private Category createEntity(CategoryRequestDTO request) {
        validateUnique(request.getName());

        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());

        return categoryRepository.save(category);
    }

    private Category updateEntity(UUID id, CategoryUpdateRequestDTO request) {
        Category category = getCategoryByIdOrException(id);

        if (ValidationUtil.isValid(request.getName(), category.getName())) {
            validateUnique(request.getName());
            category.setName(request.getName());
        }

        if (ValidationUtil.isValid(request.getDescription(), category.getDescription())) category.setDescription(request.getDescription());

        return categoryRepository.save(category);
    }

    private Category getCategoryByIdOrException(UUID id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found."));
    }

    private void validateUnique(String name) {
        boolean exists = categoryRepository.existsByNameIgnoreCase(name);

        if (exists) throw new ResourceAlreadyExistsException("Category name already exists.");
    }

    private List<CategoryResponseDTO> createResponseList(List<Category> categories) {
        return categories.stream().map(mapper::toDTO).toList();
    }
}
