package com.example.armaria.errors;

public class ArmoryKeeperNotFoundException extends RuntimeException {
  public ArmoryKeeperNotFoundException(String message) {
    super(message);
  }
}
