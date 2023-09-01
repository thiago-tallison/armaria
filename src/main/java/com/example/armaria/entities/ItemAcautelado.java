package com.example.armaria.entities;

import lombok.Data;

@Data
public class ItemAcautelado {
  private long id;
  private final Equipamento equipamento;
  private final int quantidadeAcautelada;
}
