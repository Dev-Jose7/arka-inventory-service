package com.arka.inventory_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
public class ProductRequestDTO {

    @NotBlank
    @Size(max = 150, message = "Name must be at most 150 characters.")
    private String name;

    @NotBlank(message = "Description is required.")
    private String description;

    @NotNull(message = "Brand ID is required.")
    private UUID brandId;

    @NotNull(message = "Category ID is required.")
    private UUID categoryId;
}
