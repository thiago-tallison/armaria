package com.example.armaria.dtos.acautelamento;

import java.time.LocalDateTime;
import java.util.List;

import com.example.armaria.use_cases.acautelamento.ItemAcauteladoDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CriarAcautelamentoDTO(
    @NotNull(message = "dataAcautelamento cannot be null") LocalDateTime dataAcautelamento,
    @NotBlank(message = "matriculaGm cannot be null") String matriculaGm,
    @NotBlank(message = "matriculaArmeiro cannot be null") String matriculaArmeiro,
    @NotNull(message = "itensAcautelados cannot be null") List<ItemAcauteladoDTO> itensAcautelados) {
}