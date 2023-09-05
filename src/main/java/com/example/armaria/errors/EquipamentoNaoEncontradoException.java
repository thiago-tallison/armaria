package com.example.armaria.errors;

public class EquipamentoNaoEncontradoException extends RuntimeException {
  public EquipamentoNaoEncontradoException() {
    super("Equipamento não encontrado");
  }
}
