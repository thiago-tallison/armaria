package com.example.armaria.entities;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class ItemEstoque {
  private Equipamento equipamento;
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
