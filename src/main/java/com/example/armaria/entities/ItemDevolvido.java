package com.example.armaria.entities;

import lombok.Data;

@Data
public class ItemDevolvido {
  private Long id;
  private final Equipamento equipamento;
  private int quantidadeDevolvida;

  public ItemDevolvido(Equipamento equipamento, int quantidadeDevolvida) {
    this.equipamento = equipamento;
    setQuantidadeDevolvida(quantidadeDevolvida);
  }

  public void setQuantidadeDevolvida(int quantidadeDevolvida) {
    if (quantidadeDevolvida <= 0) {
      throw new IllegalArgumentException("Não é possível devolver um item em quantidade menor ou igual a zero");
    }

    this.quantidadeDevolvida = quantidadeDevolvida;
  }

}
