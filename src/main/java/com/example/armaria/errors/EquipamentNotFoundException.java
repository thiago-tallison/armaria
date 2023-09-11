package com.example.armaria.errors;

public class EquipamentNotFoundException extends RuntimeException {
  public EquipamentNotFoundException() {
    super("Equipament não encontrado");
  }

  public EquipamentNotFoundException(String numSerie) {
    super("Equipament não encontrado: " + numSerie);
  }
}
