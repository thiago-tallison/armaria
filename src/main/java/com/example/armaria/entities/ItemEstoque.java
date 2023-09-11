package com.example.armaria.entities;

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
@Table(name = "itens_estoque")
public class ItemEstoque {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "equipament_id", referencedColumnName = "id")
  private Equipament equipament;

  @Column(name = "quantidade_em_estoque")
  private int quantityInStock;

  @Column(name = "disponivel", columnDefinition = "boolean default true")
  private boolean disponivel = true;

  public ItemEstoque(Long id) {
    this.id = id;
  }

  public ItemEstoque(Equipament equipament) {
    this.equipament = equipament;
  }

  public ItemEstoque(Equipament equipament, int quantityInStock) {
    this.equipament = equipament;
    this.quantityInStock = quantityInStock;
  }

  public void aumentarQuantidade(int quantidade) {
    if (quantidade <= 0) {
      throw new IllegalArgumentException("Qauntidade a ser aumentada precisa ser maior ou igual a zero.");
    }

    quantityInStock += quantidade;
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
