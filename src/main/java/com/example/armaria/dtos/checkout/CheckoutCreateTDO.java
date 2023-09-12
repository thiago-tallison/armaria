package com.example.armaria.dtos.checkout;

import java.time.LocalDateTime;
import java.util.List;

import com.example.armaria.use_cases.checkout.ItemAcauteladoDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CheckoutCreateTDO(
    @NotNull(message = "checkoutDate cannot be null") LocalDateTime checkoutDate,
    @NotBlank(message = "municipalGuardRegistration cannot be null") String municipalGuardRegistration,
    @NotBlank(message = "armoryKeeperRegistration cannot be null") String armoryKeeperRegistration,
    @NotNull(message = "itensAcautelados cannot be null") List<ItemAcauteladoDTO> itens) {
}