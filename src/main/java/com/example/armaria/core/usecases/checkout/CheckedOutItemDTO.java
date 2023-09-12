package com.example.armaria.core.usecases.checkout;

import jakarta.validation.constraints.NotNull;

public record CheckedOutItemDTO(
                Long id,
                @NotNull Long equipamentId,
                @NotNull Integer quantityCheckedOut) {

}
