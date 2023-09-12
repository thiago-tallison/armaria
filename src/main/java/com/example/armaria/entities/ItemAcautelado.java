package com.example.armaria.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "itens_acautelados")
public class ItemAcautelado {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne
  @JoinColumn(name = "equipament_id")
  private final Equipament equipament;

  @OneToOne
  @JoinColumn(name = "stock_item_id")
  private ItemEstoque itemEstoque;

  @Column(name = "checkout_quantity")
  private int checkoutQuantity;

  @ManyToOne
  @JoinColumn(name = "checkout_id")
  private Checkout checkout;

  public ItemAcautelado(Equipament equipament, ItemEstoque itemEstoque, int quantidadeAcautelada) {
    this.equipament = equipament;
    this.itemEstoque = itemEstoque;
    setCheckoutQuantity(quantidadeAcautelada);
  }

  public ItemAcautelado(ItemEstoque itemEstoque, int quantidadeAcautelada) {
    this.itemEstoque = itemEstoque;
    setCheckoutQuantity(quantidadeAcautelada);
    this.equipament = new Equipament();
  }

  public ItemAcautelado(Equipament equipament, int quantidadeAcautelada) {
    this.equipament = equipament;
    setCheckoutQuantity(quantidadeAcautelada);
  }

  public void setCheckoutQuantity(int quantidadeAcautelada) {
    if (quantidadeAcautelada <= 0) {
      throw new IllegalArgumentException("Não é possível acautelar um item em quantidade menor ou igual a zero");
    }

    this.checkoutQuantity = quantidadeAcautelada;
  }
}
