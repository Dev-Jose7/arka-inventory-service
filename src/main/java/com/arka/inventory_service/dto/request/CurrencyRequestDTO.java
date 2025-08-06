package com.arka.inventory_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CurrencyRequestDTO {

    @NotBlank(message = "Name is required.")
    @Size(max = 100, message = "Name must be at most 100 characters.")
    private String name;

    @NotBlank(message = "Symbol is required.")
    @Size(max = 5, message = "Symbol must be at most 5 characters.")
    private String symbol;

    @NotBlank(message = "Code is required.")
    @Size(max = 10, message = "Code must be at most 10 characters.")
    private String code;
}
