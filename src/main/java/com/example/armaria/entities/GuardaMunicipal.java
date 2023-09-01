package com.example.armaria.entities;

import lombok.Data;
import lombok.NonNull;

@Data
public class GuardaMunicipal {
  @NonNull
  private String matricula;

  @NonNull
  private String nome;

  @NonNull
  private String email;

  @NonNull
  private String telefone;

}
