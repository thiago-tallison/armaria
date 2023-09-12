package com.example.armaria.dtos.stock_item;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record IncreaseStockItemQuantityDTO(
                @NotNull(message = "quantity is required") @Min(value = 1, message = "quantidade must be greater than 0") Integer quantity) {
}