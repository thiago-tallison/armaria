package com.example.armaria.errors;

public class EquipamentoExistenteException extends RuntimeException {
  public EquipamentoExistenteException(String numSerie) {
    super(numSerie);
  }
}
