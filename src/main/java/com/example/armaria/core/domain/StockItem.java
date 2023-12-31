package com.example.armaria.core.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Table(name = "stock_items")
public class StockItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "equipament_id", referencedColumnName = "id")
  private Equipament equipament;

  @Column(name = "quantidade_em_estoque")
  private int quantityInStock;

  @Column(name = "available", columnDefinition = "boolean default true")
  private boolean available = true;

  public StockItem(Long id) {
    this.id = id;
  }

  public StockItem(Equipament equipament) {
    this.equipament = equipament;
  }

  public StockItem(Equipament equipament, int quantityInStock) {
    this.equipament = equipament;
    this.quantityInStock = quantityInStock;
  }

  public void aumentarQuantidade(int quantity) {
    if (quantity <= 0) {
      throw new IllegalArgumentException("Qauntidade a ser aumentada precisa ser maior ou igual a zero.");
    }

    quantityInStock += quantity;
  }

  public void diminuirQuantidade(int quantity) {
    if (quantity <= 0) {
      throw new IllegalArgumentException("Qauntidade a ser diminuida precisa ser maior ou igual a zero.");
    }

    if (quantity > quantityInStock) {
      throw new IllegalArgumentException(
          "Qauntidade a ser diminuida precisa ser menor ou igual à quantidade em estoque.");
    }

    quantityInStock -= quantity;
  }

}
