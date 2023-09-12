package com.example.armaria.errors;

public class ArmorerNotFoundException extends RuntimeException {
  public ArmorerNotFoundException(String message) {
    super(message);
  }
}
