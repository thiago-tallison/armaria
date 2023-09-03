package com.example.armaria.use_cases.equipamento;

public record CriarEquipamentoComItemDTO(
        String nome,
        String numSerie,
        Boolean requerDevolucao,
        Integer quantidadeEmEstoque) {

}
