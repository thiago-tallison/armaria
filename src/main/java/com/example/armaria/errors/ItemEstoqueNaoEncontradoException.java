package com.example.armaria.errors;

public class ItemEstoqueNaoEncontradoException extends RuntimeException {
  public ItemEstoqueNaoEncontradoException() {
    super("Item não encontrado no estoque");
  }
}
