package com.example.armaria.errors;

public class GuardaMunicipalNaoEncontradoException extends RuntimeException {
  public GuardaMunicipalNaoEncontradoException(String matricula) {
    super("Guarda municipal não enconrtado: " + matricula);
  }

}
