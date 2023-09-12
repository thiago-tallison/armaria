package com.example.armaria.errors;

public class ArmorerAlreadyExistsException extends RuntimeException {
  public ArmorerAlreadyExistsException(String registration) {
    super(registration);
  }
}