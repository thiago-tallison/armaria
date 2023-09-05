package com.example.armaria.errors;

public class ArmeiroJaCadastradoException extends RuntimeException {
  public ArmeiroJaCadastradoException(String matricula) {
    super(matricula);
  }
}