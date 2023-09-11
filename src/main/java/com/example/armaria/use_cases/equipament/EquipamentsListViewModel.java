package com.example.armaria.use_cases.equipament;

import java.util.List;

import com.example.armaria.entities.Equipament;

public record EquipamentsListViewModel(
                Integer currentPage,
                String nextPage,
                String previousPage,
                long totalItens,
                Integer totalPages,
                List<Equipament> itens) {
}
