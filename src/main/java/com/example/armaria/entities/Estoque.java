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
  @JoinColumn(name = "stock_item_id")
  private List<ItemEstoque> itensEmEstoque = new ArrayList<>();

  public void adicionarQuantidade(Equipament equipament, int quantidade) {
    ItemEstoque item = encontrarItemNoEstoque(equipament);

    if (item != null) {
      item.aumentarQuantidade(quantidade);
    } else {
      adicionarItemEmEstoque(equipament, quantidade);
    }
  }

  public void removerQuantidade(Equipament equipament, int quantidade) {
    ItemEstoque item = encontrarItemNoEstoque(equipament);

    if (item != null) {
      item.diminuirQuantidade(quantidade);
    }
  }

  public void adicionarItemEmEstoque(Equipament equipament, int quantidade) {
    ItemEstoque item = new ItemEstoque(equipament);
    item.aumentarQuantidade(quantidade);

    itensEmEstoque.add(item);
  }

  public void removerItemDoEstoque(Equipament equipament) {
    ItemEstoque item = encontrarItemNoEstoque(equipament);

    if (item != null) {
      itensEmEstoque.remove(item);
    }
  }

  public int getQuantityInStock(Equipament equipament) {
    ItemEstoque item = encontrarItemNoEstoque(equipament);

    if (item != null) {
      return item.getQuantityInStock();
    }

    return 0; // returns 0 if item is not in stock
  }

  public void checkOutEquipaments(List<ItemAcautelado> itensAcautelados) {
    for (ItemAcautelado itemAcautelado : itensAcautelados) {
      ItemEstoque itemEstoque = encontrarItemNoEstoque(itemAcautelado);
      itemEstoque.diminuirQuantidade(itemAcautelado.getCheckoutQuantity());
    }
  }

  public void returnEquipaments(Map<Equipament, Integer> returnedEquipaments) {
    for (Map.Entry<Equipament, Integer> entry : returnedEquipaments.entrySet()) {
      Equipament equipament = entry.getKey();
      int quantidadeDevolvida = entry.getValue();
      ItemEstoque itemEstoque = encontrarItemNoEstoque(equipament);

      if (itemEstoque == null) {
        throw new ItemEstoqueNaoDisponivelException("Item não disponível em estoque: " + equipament.toString());
      }

      itemEstoque.aumentarQuantidade(quantidadeDevolvida);
    }
  }

  private ItemEstoque encontrarItemNoEstoque(Equipament equipament) {
    for (ItemEstoque item : itensEmEstoque) {
      if (item.getEquipament().equals(equipament)) {
        return item;
      }
    }
    return null;
  }

  private ItemEstoque encontrarItemNoEstoque(ItemAcautelado itemAcautelado) {
    for (ItemEstoque item : itensEmEstoque) {
      if (item.getEquipament().equals(itemAcautelado.getEquipament())) {
        return item;
      }
    }
    return null;
  }
}