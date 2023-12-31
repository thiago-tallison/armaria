package com.example.armaria.core.domain;

import com.example.armaria.presenter.rest.api.municipal_guard.MunicipalGuardCreateDTO;

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
@Table(name = "municipal_guards")
public class MunicipalGuard {
  @NonNull
  @Id
  @Column(name = "registration_number")
  private String registrationNumber;

  @NonNull
  private String name;

  @NonNull
  private String email;

  @NonNull
  private String phone;

  public MunicipalGuard(MunicipalGuardCreateDTO dto) {
    this.registrationNumber = dto.registrationNumber();
    this.name = dto.name();
    this.email = dto.email();
    this.phone = dto.phone();
  }

}
