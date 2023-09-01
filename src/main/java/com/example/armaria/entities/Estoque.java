package com.example.armaria.entities;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class Estoque {
  private Long id;
  private Map<Equipamento, ItemEstoque> itensEmEstoque = new HashMap<>();

  public void adicionarItemEmEstoque(Equipamento equipamento, int quantidade) {
    ItemEstoque itemEstoque = itensEmEstoque.getOrDefault(equipamento, new ItemEstoque(equipamento));
    itemEstoque.aumentarQuantidade(quantidade);
    itensEmEstoque.put(equipamento, itemEstoque);
  }

  public void removerItemDoEstoque(Equipamento equipamento, int quantidade) {
    ItemEstoque itemEstoque = itensEmEstoque.get(equipamento);
    if (itemEstoque != null) {
      itemEstoque.diminuirQuantidade(quantidade);
      if (itemEstoque.getQuantidadeDisponivel() == 0) {
        itensEmEstoque.remove(equipamento);
      }
    }
  }

  public Integer getQuantidadeEmEstoque(Equipamento equipamento) {
    ItemEstoque itemEstoque = itensEmEstoque.get(equipamento);
    return itemEstoque != null ? itemEstoque.getQuantidadeDisponivel() : 0;
  }
}