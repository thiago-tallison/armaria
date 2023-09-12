package com.example.armaria.core.domain;

import com.example.armaria.core.usecases.equipament.EquipamentCreateDTO;

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
@Table(name = "equipaments")
public class Equipament {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NonNull
  private String name;

  @NonNull
  @Column(name = "serial_number")
  private String serialNumber;

  @NonNull
  @Column(name = "require_return")
  private Boolean requireReturn;

  public Equipament(EquipamentCreateDTO dto) {
    this.name = dto.name();
    this.serialNumber = dto.serialNumber();
    this.requireReturn = dto.requireReturn();
  }
}
