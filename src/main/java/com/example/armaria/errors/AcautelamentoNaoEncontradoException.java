package com.example.armaria.errors;

public class AcautelamentoNaoEncontradoException extends RuntimeException {
  public AcautelamentoNaoEncontradoException(Long idAcautelamento) {
    super("Acautelamento com id " + idAcautelamento + " não encontrado");
  }

}
