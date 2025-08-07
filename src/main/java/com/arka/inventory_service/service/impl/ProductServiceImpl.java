package com.arka.inventory_service.service.impl;

import com.arka.inventory_service.dto.request.ProductRequestDTO;
import com.arka.inventory_service.dto.response.ProductResponseDTO;
import com.arka.inventory_service.exception.ResourceAlreadyExistsException;
import com.arka.inventory_service.exception.ResourceNotFoundException;
import com.arka.inventory_service.mapper.EntityToDTOMapper;
import com.arka.inventory_service.model.Brand;
import com.arka.inventory_service.model.Category;
import com.arka.inventory_service.model.Product;
import com.arka.inventory_service.repository.BrandRepository;
import com.arka.inventory_service.repository.CategoryRepository;
import com.arka.inventory_service.repository.ProductRepository;
import com.arka.inventory_service.service.IProductService;
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

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO request) {
        return mapper.toDTO(productRepository.save(createEntity(request)));
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

    private Product getProductByIdOrException(UUID id){
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found."));
    }

    private Brand getBrandByIdOrException(UUID id){
        return brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found."));
    }

    private Category getCategoryByIdOrException(UUID id){
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found."));
    }

    private void validateUnique(String name, UUID brandId){
        boolean check = productRepository.existsByNameIgnoreCaseAndBrandId(name, brandId);

        if (check) throw new ResourceAlreadyExistsException("Product name already exists with that brand");
    }

    private Product createEntity(ProductRequestDTO request){
        validateUnique(request.getName(), request.getBrandId());
        Product product = new Product();

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setBrand(getBrandByIdOrException(request.getBrandId()));
        product.setCategory(getCategoryByIdOrException(request.getCategoryId()));

        return product;
    }

    private List<ProductResponseDTO> createResponseList(List<Product> product){
        return product.stream().map(mapper::toDTO).toList();
    }
}
