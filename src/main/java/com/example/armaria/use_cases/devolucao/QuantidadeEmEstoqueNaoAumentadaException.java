package com.example.armaria.use_cases.devolucao;

public class QuantidadeEmEstoqueNaoAumentadaException extends RuntimeException {
  public QuantidadeEmEstoqueNaoAumentadaException(Long idItemEstoque) {
    super("Não foi possivel aumentar a quantidade em estoque do item: " + idItemEstoque
        + ". Verifique: se o item existe; se a quantidade a ser devolvida é menor ou igual a quantidade acautelada; e se está tentando diminuir uma quantidade maior ou igual a zero.");
  }
}
