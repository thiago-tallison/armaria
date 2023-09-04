package com.example.armaria.errors;

public class TamanhoDaPaginaNegativoException extends IllegalArgumentException {
  public TamanhoDaPaginaNegativoException(int tamanho) {
    super("Tamanho da página não pode ser negativo. Tentando recuperar " + tamanho + " itens por página.");
  }
}
