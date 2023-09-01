package com.example.armaria.entities;

import lombok.Data;
import lombok.NonNull;

@Data
public class Equipamento {
  Long id;
  @NonNull
  String nome;
  @NonNull
  String numSerie;
  @NonNull
  Boolean requerDevolucao;

}
