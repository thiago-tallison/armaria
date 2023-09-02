package com.example.armaria.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NonNull;

@Data
@Entity
@Table(name = "armeiros")
public class Armeiro {
  @Id
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
  private String senha;

}
