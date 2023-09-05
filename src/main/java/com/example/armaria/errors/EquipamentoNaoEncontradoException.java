package com.example.armaria.errors;

public class EquipamentoNaoEncontradoException extends RuntimeException {
  public EquipamentoNaoEncontradoException() {
    super("Equipamento não encontrado");
  }

  public EquipamentoNaoEncontradoException(String numSerie) {
    super("Equipamento não encontrado: " + numSerie);
  }
}
