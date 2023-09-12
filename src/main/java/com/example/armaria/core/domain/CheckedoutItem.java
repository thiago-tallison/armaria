package com.example.armaria.core.domain;

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
public class CheckedoutItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne
  @JoinColumn(name = "equipament_id")
  private final Equipament equipament;

  @OneToOne
  @JoinColumn(name = "stock_item_id")
  private StockItem stockItem;

  @Column(name = "checkout_quantity")
  private int checkoutQuantity;

  @ManyToOne
  @JoinColumn(name = "checkout_id")
  private Checkout checkout;

  public CheckedoutItem(Equipament equipament, StockItem stockItem, int checkedoutQuantity) {
    this.equipament = equipament;
    this.stockItem = stockItem;
    setCheckoutQuantity(checkedoutQuantity);
  }

  public CheckedoutItem(StockItem stockItem, int checkedoutQuantity) {
    this.stockItem = stockItem;
    setCheckoutQuantity(checkedoutQuantity);
    this.equipament = new Equipament();
  }

  public CheckedoutItem(Equipament equipament, int checkedoutQuantity) {
    this.equipament = equipament;
    setCheckoutQuantity(checkedoutQuantity);
  }

  public void setCheckoutQuantity(int checkedoutQuantity) {
    if (checkedoutQuantity <= 0) {
      throw new IllegalArgumentException("Não é possível acautelar um item em quantidade menor ou igual a zero");
    }

    this.checkoutQuantity = checkedoutQuantity;
  }
}
