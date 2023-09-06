package com.example.armaria.use_cases.acautelamento;

import jakarta.validation.constraints.NotNull;

public record ItemAcauteladoDTO(
        Long id,
        @NotNull Long idEquipamento,
        @NotNull Integer quantidadeAcautelada) {

}
