package com.arka.inventory_service.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
public class ProductUpdateRequestDTO {

    @Size(max = 150, message = "Name must be at most 150 characters.")
    private String name;

    private String description;

    private UUID brandId;

    private UUID categoryId;
}
