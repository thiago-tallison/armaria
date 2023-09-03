package com.example.armaria.entities;

import com.example.armaria.use_cases.armeiro.AtualizarArmeiroDTO;
import com.example.armaria.use_cases.armeiro.CriarArmeiroDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
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

  public Armeiro(CriarArmeiroDTO dto) {
    this.matricula = dto.matricula();
    this.nome = dto.nome();
    this.email = dto.email();
    this.telefone = dto.telefone();
    this.login = dto.login();
    this.senha = dto.senha();
  }

  public Armeiro(AtualizarArmeiroDTO dto) {
    this.nome = dto.nome();
    this.email = dto.email();
    this.telefone = dto.telefone();
  }
}
