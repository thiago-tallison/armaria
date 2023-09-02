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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode
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
  @JoinColumn(name = "id_equipamento", referencedColumnName = "id")
  private Equipamento equipamento;

  @Column(name = "quantidade_em_estoque")
  private int quantidadeEmEstoque;

  public ItemEstoque(Equipamento equipamento) {
    this.equipamento = equipamento;
  }

  public void aumentarQuantidade(int quantidade) {
    if (quantidade <= 0) {
      throw new IllegalArgumentException("Qauntidade a ser aumentada precisa ser maior ou igual a zero.");
    }

    quantidadeEmEstoque += quantidade;
  }

  public void diminuirQuantidade(int quantidade) {
    if (quantidade <= 0) {
      throw new IllegalArgumentException("Qauntidade a ser diminuida precisa ser maior ou igual a zero.");
    }

    if (quantidade > quantidadeEmEstoque) {
      throw new IllegalArgumentException(
          "Qauntidade a ser diminuida precisa ser menor ou igual à quantidade em estoque.");
    }

    quantidadeEmEstoque -= quantidade;
  }

  public int getQuantidadeDisponivel() {
    return quantidadeEmEstoque;
  }

}
