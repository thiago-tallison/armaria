package com.example.armaria.core.domain;

import com.example.armaria.core.usecases.armorer.ArmorerCreateDTO;
import com.example.armaria.core.usecases.armorer.ArmorerUpdateDTO;

import jakarta.persistence.Column;
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
@Table(name = "armorers")
public class Armorer {
  @Id
  @NonNull
  @Column(name = "registration_number")
  private String registrationNumber;

  @NonNull
  private String name;

  @NonNull
  private String email;

  @NonNull
  private String phone;

  @NonNull
  private String login;

  @NonNull
  private String password;

  public Armorer(ArmorerCreateDTO dto) {
    this.registrationNumber = dto.registrationNumber();
    this.name = dto.name();
    this.email = dto.email();
    this.phone = dto.phone();
    this.login = dto.login();
    this.password = dto.password();
  }

  public Armorer(ArmorerUpdateDTO dto) {
    this.name = dto.name();
    this.email = dto.email();
    this.phone = dto.phone();
  }
}
