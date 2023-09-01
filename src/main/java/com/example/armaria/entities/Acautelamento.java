package com.example.armaria.entities;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class Acautelamento {
  private Long id;
  private final LocalDateTime dataAcautelamento;
  private final GuardaMunicipal gm;
  private final Armeiro armeiro;
  private Map<ItemAcautelado, Integer> equipamentos = new HashMap<>();

  public void adicionarEquipamento(ItemAcautelado item) {
    equipamentos.put(item, equipamentos.getOrDefault(item, 0) + item.getQuantidadeAcautelada());
  }

  public void removerEquipamento(ItemAcautelado e, int quantidade) {
    if (equipamentos.get(e) <= quantidade) {
      equipamentos.remove(e);
      return;
    }

    equipamentos.put(e, equipamentos.get(e) - quantidade);
  }

}
