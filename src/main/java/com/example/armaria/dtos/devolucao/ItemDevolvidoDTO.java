package com.example.armaria.dtos.devolucao;

import jakarta.validation.constraints.NotNull;

public record ItemDevolvidoDTO(
    @NotNull(message = "id cannot be null") Long idItemEstoque,
    @NotNull(message = "quantidadeDevolvida cannot be null") Integer quantidadeDevolvida) {

}
