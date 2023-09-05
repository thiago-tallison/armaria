package com.example.armaria.use_cases.equipamento;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CriarEquipamentoComItemDTO(
                @NotBlank(message = "nome is missing") String nome,
                @NotBlank(message = "numSerie is missing") String numSerie,
                @NotNull(message = "requerDevolucao is missing") Boolean requerDevolucao,
                @NotNull(message = "quantidadeEmEstoque is missing") @Min(value = 1, message = "quantidadeEmEstoque must be grater than 0") Integer quantidadeEmEstoque) {
}
