package com.example.armaria.errors;

public class EquipamentNotFoundException extends RuntimeException {
  public EquipamentNotFoundException() {
    super("Equipament não encontrado");
  }

  public EquipamentNotFoundException(String serialNumber) {
    super("Equipament não encontrado: " + serialNumber);
  }
}
