package com.example.armaria.use_cases.equipamento;

import java.util.List;

import com.example.armaria.entities.Equipamento;

public record ListarEquipamentoResponseDTO(
                Integer paginaAtual,
                String proximaPagina,
                String paginaAnterior,
                long totalItens,
                Integer totalPaginas,
                List<Equipamento> itens) {
}
