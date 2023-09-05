package com.example.armaria.errors;

public class EquipamentoExistenteException extends RuntimeException {
  public EquipamentoExistenteException(String numSerie) {
    super("Equipamento com número de série já cadastrado: " + numSerie);
  }
}
