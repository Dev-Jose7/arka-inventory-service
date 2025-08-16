package com.arka.inventory_service.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CategoryUpdateRequestDTO {

    @Size(max = 100, message = "Name must be at most 100 characters.")
    private String name;

    private String description;
}
