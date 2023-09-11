package com.example.armaria.entities;

import com.example.armaria.use_cases.armory_keeper.ArmoryKeeperCreateDTO;
import com.example.armaria.use_cases.armory_keeper.ArmoryKeeperUpdateDTO;

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
@Table(name = "armory_keepers")
public class ArmoryKepper {
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

  public ArmoryKepper(ArmoryKeeperCreateDTO dto) {
    this.registrationNumber = dto.registrationNumber();
    this.name = dto.name();
    this.email = dto.email();
    this.phone = dto.phone();
    this.login = dto.login();
    this.password = dto.password();
  }

  public ArmoryKepper(ArmoryKeeperUpdateDTO dto) {
    this.name = dto.name();
    this.email = dto.email();
    this.phone = dto.phone();
  }
}
