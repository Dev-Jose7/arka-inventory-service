package com.arka.inventory_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BrandRequestDTO {

    @NotBlank(message = "Name is required.")
    @Size(max = 50, message = "The name must be less than 50 characters.")
    private String name;
}
