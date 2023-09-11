package com.example.armaria.errors;

public class PageSizeOutOfBoundException extends RuntimeException {
  public PageSizeOutOfBoundException() {
    super("O tamanho da página excede o limite máximo permitido.");
  }
}
