package com.example.armaria.entities;

import lombok.Data;

@Data
public class ItemAcautelado {
  private long id;
  private final Equipamento equipamento;
  private int quantidadeAcautelada;

  public ItemAcautelado(Equipamento equipamento, int quantidadeAcautelada) {
    this.equipamento = equipamento;
    setQuantidadeAcautelada(quantidadeAcautelada);
  }

  public void setQuantidadeAcautelada(int quantidadeAcautelada) {
    if (quantidadeAcautelada <= 0) {
      throw new IllegalArgumentException("Não é possível acautelar um item em quantidade menor ou igual a zero");
    }

    this.quantidadeAcautelada = quantidadeAcautelada;
  }
}
