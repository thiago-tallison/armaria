package com.example.armaria.errors;

public class EquipamentAlreadyExistsException extends RuntimeException {
  public EquipamentAlreadyExistsException(String numSerie) {
    super("Equipament com número de série já cadastrado: " + numSerie);
  }
}
