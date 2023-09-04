package com.example.armaria.entities;

import com.example.armaria.use_cases.equipamento.CriarEquipamentoComItemDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "equipamentos")
public class Equipamento {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NonNull
  private String nome;

  @NonNull
  @Column(name = "num_serie")
  private String numSerie;

  @NonNull
  @Column(name = "requer_devolucao")
  private Boolean requerDevolucao;

  public Equipamento(CriarEquipamentoComItemDTO dto) {
    this.nome = dto.nome();
    this.numSerie = dto.numSerie();
    this.requerDevolucao = dto.requerDevolucao();
  }
}
