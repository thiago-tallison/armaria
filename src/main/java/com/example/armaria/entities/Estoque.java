package com.example.armaria.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.armaria.errors.ItemEstoqueNaoDisponivelException;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "estoques")
public class Estoque {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToMany
  @JoinColumn(name = "id_item_estoque")
  private List<ItemEstoque> itensEmEstoque = new ArrayList<>();

  public void adicionarQuantidade(Equipamento equipamento, int quantidade) {
    ItemEstoque item = encontrarItemNoEstoque(equipamento);

    if (item != null) {
      item.aumentarQuantidade(quantidade);
    } else {
      adicionarItemEmEstoque(equipamento, quantidade);
    }
  }

  public void removerQuantidade(Equipamento equipamento, int quantidade) {
    ItemEstoque item = encontrarItemNoEstoque(equipamento);

    if (item != null) {
      item.diminuirQuantidade(quantidade);
    }
  }

  public void adicionarItemEmEstoque(Equipamento equipamento, int quantidade) {
    ItemEstoque item = new ItemEstoque(equipamento);
    item.aumentarQuantidade(quantidade);

    itensEmEstoque.add(item);
  }

  public void removerItemDoEstoque(Equipamento equipamento) {
    ItemEstoque item = encontrarItemNoEstoque(equipamento);

    if (item != null) {
      itensEmEstoque.remove(item);
    }
  }

  public int getQuantidadeEmEstoque(Equipamento equipamento) {
    ItemEstoque item = encontrarItemNoEstoque(equipamento);

    if (item != null) {
      return item.getQuantidadeEmEstoque();
    }

    return 0; // Retorna 0 se o equipamento não estiver no estoque
  }

  public void acautelarEquipamentos(List<ItemAcautelado> itensAcautelados) {
    for (ItemAcautelado itemAcautelado : itensAcautelados) {
      ItemEstoque itemEstoque = encontrarItemNoEstoque(itemAcautelado);
      itemEstoque.diminuirQuantidade(itemAcautelado.getQuantidadeAcautelada());
    }
  }

  public void devolverEquipamentos(Map<Equipamento, Integer> equipamentosDevolvidos) {
    for (Map.Entry<Equipamento, Integer> entry : equipamentosDevolvidos.entrySet()) {
      Equipamento equipamento = entry.getKey();
      int quantidadeDevolvida = entry.getValue();
      ItemEstoque itemEstoque = encontrarItemNoEstoque(equipamento);

      if (itemEstoque == null) {
        throw new ItemEstoqueNaoDisponivelException("Item não disponível em estoque: " + equipamento.toString());
      }

      itemEstoque.aumentarQuantidade(quantidadeDevolvida);
    }
  }

  private ItemEstoque encontrarItemNoEstoque(Equipamento equipamento) {
    for (ItemEstoque item : itensEmEstoque) {
      if (item.getEquipamento().equals(equipamento)) {
        return item;
      }
    }
    return null;
  }

  private ItemEstoque encontrarItemNoEstoque(ItemAcautelado itemAcautelado) {
    for (ItemEstoque item : itensEmEstoque) {
      if (item.getEquipamento().equals(itemAcautelado.getEquipamento())) {
        return item;
      }
    }
    return null;
  }
}