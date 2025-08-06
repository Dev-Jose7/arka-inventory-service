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
public class WarehouseRequestDTO {

    @NotBlank(message = "Name is required.")
    @Size(min = 100, message = "Name must be at most 100 characters.")
    private String name;

    @NotBlank(message = "Location is required.")
    @Size(min = 200, message = "Location must be at most 200 characters.")
    private String location;

    @NotNull(message = "Country id is required.")
    private UUID countryId;
}
