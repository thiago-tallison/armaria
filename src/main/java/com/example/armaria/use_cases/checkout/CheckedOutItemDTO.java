package com.example.armaria.use_cases.checkout;

import jakarta.validation.constraints.NotNull;

public record CheckedOutItemDTO(
        Long id,
        @NotNull Long equipamentId,
        @NotNull Integer quantityCheckedOut) {

}
