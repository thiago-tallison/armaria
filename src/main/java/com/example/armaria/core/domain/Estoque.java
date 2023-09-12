package com.example.armaria.core.domain;

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
  private List<StockItem> itensEmEstoque = new ArrayList<>();

  public void adicionarQuantidade(Equipament equipament, int quantity) {
    StockItem item = encontrarItemNoEstoque(equipament);

    if (item != null) {
      item.aumentarQuantidade(quantity);
    } else {
      adicionarItemEmEstoque(equipament, quantity);
    }
  }

  public void removerQuantidade(Equipament equipament, int quantity) {
    StockItem item = encontrarItemNoEstoque(equipament);

    if (item != null) {
      item.diminuirQuantidade(quantity);
    }
  }

  public void adicionarItemEmEstoque(Equipament equipament, int quantity) {
    StockItem item = new StockItem(equipament);
    item.aumentarQuantidade(quantity);

    itensEmEstoque.add(item);
  }

  public void removerItemDoEstoque(Equipament equipament) {
    StockItem item = encontrarItemNoEstoque(equipament);

    if (item != null) {
      itensEmEstoque.remove(item);
    }
  }

  public int getQuantityInStock(Equipament equipament) {
    StockItem item = encontrarItemNoEstoque(equipament);

    if (item != null) {
      return item.getQuantityInStock();
    }

    return 0; // returns 0 if item is not in stock
  }

  public void checkOutEquipaments(List<CheckedoutItem> itensAcautelados) {
    for (CheckedoutItem checkedoutItem : itensAcautelados) {
      StockItem stockItem = encontrarItemNoEstoque(checkedoutItem);
      stockItem.diminuirQuantidade(checkedoutItem.getCheckoutQuantity());
    }
  }

  public void returnEquipaments(Map<Equipament, Integer> returnedEquipaments) {
    for (Map.Entry<Equipament, Integer> entry : returnedEquipaments.entrySet()) {
      Equipament equipament = entry.getKey();
      int quantidadeDevolvida = entry.getValue();
      StockItem stockItem = encontrarItemNoEstoque(equipament);

      if (stockItem == null) {
        throw new ItemEstoqueNaoDisponivelException("Item não disponível em estoque: " + equipament.toString());
      }

      stockItem.aumentarQuantidade(quantidadeDevolvida);
    }
  }

  private StockItem encontrarItemNoEstoque(Equipament equipament) {
    for (StockItem item : itensEmEstoque) {
      if (item.getEquipament().equals(equipament)) {
        return item;
      }
    }
    return null;
  }

  private StockItem encontrarItemNoEstoque(CheckedoutItem checkedoutItem) {
    for (StockItem item : itensEmEstoque) {
      if (item.getEquipament().equals(checkedoutItem.getEquipament())) {
        return item;
      }
    }
    return null;
  }
}