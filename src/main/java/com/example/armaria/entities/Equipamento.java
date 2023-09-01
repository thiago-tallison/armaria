package com.example.armaria.entities;

import lombok.Data;
import lombok.NonNull;

@Data
public class Equipamento {
  private Long id;

  @NonNull
  private String nome;

  @NonNull
  private String numSerie;

  @NonNull
  Boolean requerDevolucao;

}
