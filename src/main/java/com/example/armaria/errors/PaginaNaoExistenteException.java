package com.example.armaria.errors;

public class PaginaNaoExistenteException extends RuntimeException {
  public PaginaNaoExistenteException() {
    super("A página solicitada não existe.");
  }
}
