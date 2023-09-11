package com.example.armaria.errors;

public class MunicipalGuardNotFoundException extends RuntimeException {
  public MunicipalGuardNotFoundException(String registrationNumber) {
    super("Guarda municipal não enconrtado: " + registrationNumber);
  }

}
