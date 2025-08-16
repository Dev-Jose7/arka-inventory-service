package com.arka.inventory_service.service.impl;

import com.arka.inventory_service.dto.request.ProductRequestDTO;
import com.arka.inventory_service.dto.request.ProductUpdateRequestDTO;
import com.arka.inventory_service.dto.response.ProductResponseDTO;
import com.arka.inventory_service.exception.ResourceAlreadyExistsException;
import com.arka.inventory_service.exception.ResourceNotFoundException;
import com.arka.inventory_service.mapper.EntityToDTOMapper;
import com.arka.inventory_service.model.Brand;
import com.arka.inventory_service.model.Category;
import com.arka.inventory_service.model.Product;
import com.arka.inventory_service.notification.NotificationService;
import com.arka.inventory_service.repository.BrandRepository;
import com.arka.inventory_service.repository.CategoryRepository;
import com.arka.inventory_service.repository.ProductRepository;
import com.arka.inventory_service.service.IProductService;
import com.arka.inventory_service.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final EntityToDTOMapper mapper;
    private final NotificationService notificationService;

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO request) {
        Product product = productRepository.save(createEntity(request));
        notificationService.notifyNewProduct(product);
        return mapper.toDTO(product);
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        return createResponseList(productRepository.findAll());
    }

    @Override
    public List<ProductResponseDTO> getProductsByName(String name) {
        return createResponseList(productRepository.findAllByNameIgnoreCase(name));
    }

    @Override
    public List<ProductResponseDTO> getProductByBrandId(UUID brandId) {
        return createResponseList(productRepository.findAllByBrandId(brandId));
    }

    @Override
    public List<ProductResponseDTO> getProductByCategoryId(UUID categoryId) {
        return createResponseList(productRepository.findAllByCategoryId(categoryId));
    }

    @Override
    public ProductResponseDTO updateProduct(UUID id, ProductUpdateRequestDTO request) {
        return mapper.toDTO(updateEntity(id, request));
    }

    // --- Private utility methods ---

    private Product createEntity(ProductRequestDTO request) {
        validateUnique(request.getName(), request.getBrandId());

        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setBrand(getBrandByIdOrException(request.getBrandId()));
        product.setCategory(getCategoryByIdOrException(request.getCategoryId()));

        return product;
    }

    private Product updateEntity(UUID id, ProductUpdateRequestDTO request) {
        Product product = getProductByIdOrException(id);

        if (ValidationUtil.isValid(request.getName(), product.getName())) {
            validateUnique(request.getName(), product.getBrand().getId());
            product.setName(request.getName());
        }

        if (ValidationUtil.isValid(request.getDescription(), product.getDescription())) {
            product.setDescription(request.getDescription());
        }

        if (ValidationUtil.isValid(request.getBrandId(), product.getBrand().getId())) {
            product.setBrand(getBrandByIdOrException(request.getBrandId()));
        }

        if (ValidationUtil.isValid(request.getCategoryId(), product.getCategory().getId())) {
            product.setCategory(getCategoryByIdOrException(request.getCategoryId()));
        }

        return productRepository.save(product);
    }

    private Product getProductByIdOrException(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found."));
    }

    private Brand getBrandByIdOrException(UUID id) {
        return brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found."));
    }

    private Category getCategoryByIdOrException(UUID id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found."));
    }

    private void validateUnique(String name, UUID brandId) {
        boolean exists = productRepository.existsByNameIgnoreCaseAndBrandId(name, brandId);
        if (exists) throw new ResourceAlreadyExistsException("Product name already exists with that brand.");
    }

    private List<ProductResponseDTO> createResponseList(List<Product> products) {
        return products.stream().map(mapper::toDTO).toList();
    }
}
