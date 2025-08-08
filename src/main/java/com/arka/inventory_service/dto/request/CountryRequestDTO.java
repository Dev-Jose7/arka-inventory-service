package com.arka.inventory_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CountryRequestDTO {

    @NotBlank(message = "Name is required.")
    private String name;
}
