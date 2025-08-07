package com.arka.inventory_service.service.impl;

import com.arka.inventory_service.dto.request.BrandRequestDTO;
import com.arka.inventory_service.dto.response.BrandResponseDTO;
import com.arka.inventory_service.exception.ResourceAlreadyExistsException;
import com.arka.inventory_service.exception.ResourceNotFoundException;
import com.arka.inventory_service.mapper.EntityToDTOMapper;
import com.arka.inventory_service.model.Brand;
import com.arka.inventory_service.repository.BrandRepository;
import com.arka.inventory_service.service.IBrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements IBrandService {

    private final BrandRepository brandRepository;
    private final EntityToDTOMapper mapper;

    @Override
    public BrandResponseDTO createBrand(BrandRequestDTO request) {
        return mapper.toDTO(createEntity(request));
    }

    @Override
    public BrandResponseDTO getBrandById(UUID id) {
        Brand brand = getBrandByIdOrException(id);
        return mapper.toDTO(brand);
    }

    @Override
    public BrandResponseDTO getBrandByName(String name) {
        Brand brand = brandRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found."));
        return mapper.toDTO(brand);
    }

    @Override
    public List<BrandResponseDTO> getAllBrands() {
        return createResponseList(brandRepository.findAll());
    }

    // --- Private utilitarian methods ---

    private Brand createEntity(BrandRequestDTO request) {
        validateUnique(request.getName());

        Brand brand = new Brand();
        brand.setName(request.getName());

        return brandRepository.save(brand);
    }

    private Brand getBrandByIdOrException(UUID id) {
        return brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found."));
    }

    private void validateUnique(String name) {
        boolean exists = brandRepository.existsByNameIgnoreCase(name);

        if (exists) throw new ResourceAlreadyExistsException("Brand name already exists.");
    }

    private List<BrandResponseDTO> createResponseList(List<Brand> brands) {
        return brands.stream().map(mapper::toDTO).toList();
    }
}
