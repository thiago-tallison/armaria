package com.example.armaria.errors;

public class ItemEstoqueNaoDisponivelException extends RuntimeException {
  public ItemEstoqueNaoDisponivelException(String message) {
    super(message);
  }

}
