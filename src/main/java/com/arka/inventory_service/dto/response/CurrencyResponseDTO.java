package com.arka.inventory_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@AllArgsConstructor
@Getter
@ToString
public class CurrencyResponseDTO {

    private UUID id;
    private String code;
}
