package com.example.armaria.entities;

import com.example.armaria.controllers.guarda_municipal.CriarGuardaMunicipalDTO;

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
@Table(name = "guardas_municipais")
public class GuardaMunicipal {
  @NonNull
  @Id
  private String matricula;

  @NonNull
  private String nome;

  @NonNull
  private String email;

  @NonNull
  private String telefone;

  public GuardaMunicipal(CriarGuardaMunicipalDTO dto) {
    this.matricula = dto.matricula();
    this.nome = dto.nome();
    this.email = dto.email();
    this.telefone = dto.telefone();
  }

}
