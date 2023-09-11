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
  @JoinColumn(name = "id_item_estoque")
  private ItemEstoque itemEstoque;

  @Column(name = "quantidade_acautelada")
  private int quantidadeAcautelada;

  @ManyToOne
  @JoinColumn(name = "id_acautelamento")
  private Acautelamento acautelamento;

  public ItemAcautelado(Equipament equipament, ItemEstoque itemEstoque, int quantidadeAcautelada) {
    this.equipament = equipament;
    this.itemEstoque = itemEstoque;
    setQuantidadeAcautelada(quantidadeAcautelada);
  }

  public ItemAcautelado(ItemEstoque itemEstoque, int quantidadeAcautelada) {
    this.itemEstoque = itemEstoque;
    setQuantidadeAcautelada(quantidadeAcautelada);
    this.equipament = new Equipament();
  }

  public ItemAcautelado(Equipament equipament, int quantidadeAcautelada) {
    this.equipament = equipament;
    setQuantidadeAcautelada(quantidadeAcautelada);
  }

  public void setQuantidadeAcautelada(int quantidadeAcautelada) {
    if (quantidadeAcautelada <= 0) {
      throw new IllegalArgumentException("Não é possível acautelar um item em quantidade menor ou igual a zero");
    }

    this.quantidadeAcautelada = quantidadeAcautelada;
  }
}
