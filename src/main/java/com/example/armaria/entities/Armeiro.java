package com.example.armaria.entities;

import lombok.Data;
import lombok.NonNull;

@Data
public class Armeiro {
  @NonNull
  private String matricula;

  @NonNull
  private String nome;

  @NonNull
  private String email;

  @NonNull
  private String telefone;

  @NonNull
  private String login;

  @NonNull
  private String snha;

}
