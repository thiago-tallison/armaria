package com.example.armaria.use_cases.equipament;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EquipamentCreateDTO(
        @NotBlank(message = "name is missing") String name,
        @NotBlank(message = "serialNumber is missing") String serialNumber,
        @NotNull(message = "requireReturn is missing") Boolean requireReturn,
        @NotNull(message = "quantityInStock is missing") @Min(value = 1, message = "quantityInStock must be grater than 0") Integer quantityInStock) {
}
