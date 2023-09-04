package com.example.armaria.errors;

public class TamanhoDePaginaExcedeuOLimiteException extends RuntimeException {
  public TamanhoDePaginaExcedeuOLimiteException() {
    super("O tamanho da página excede o limite máximo permitido.");
  }
}
