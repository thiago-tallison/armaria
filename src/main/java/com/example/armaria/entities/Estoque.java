package com.example.armaria.entities;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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

  public void acautelarEquipamentos(Map<Equipamento, Integer> equipamentosAcautelados) {
    for (Entry<Equipamento, Integer> entry : equipamentosAcautelados.entrySet()) {
      Equipamento equipamento = entry.getKey();
      int quantidadeAcautelada = entry.getValue();
      ItemEstoque itemEstoque = itensEmEstoque.get(equipamento);

      if (itemEstoque == null || itemEstoque.getQuantidadeDisponivel() < quantidadeAcautelada) {
        throw new Error("Item não disponível em estoque: " + equipamento.toString());
      }

      itemEstoque.diminuirQuantidade(quantidadeAcautelada);
    }
  }

  public void devolverEquipamentos(Map<Equipamento, Integer> equipamentosDevolvidos) {
    for (Entry<Equipamento, Integer> entry : equipamentosDevolvidos.entrySet()) {
      Equipamento equipamento = entry.getKey();
      int quantidadeDevolvida = entry.getValue();
      ItemEstoque itemEstoque = itensEmEstoque.get(equipamento);

      if (itemEstoque == null) {
        throw new Error("Item não disponível em estoque: " + equipamento.toString());
      }

      itemEstoque.aumentarQuantidade(quantidadeDevolvida);
    }
  }
}