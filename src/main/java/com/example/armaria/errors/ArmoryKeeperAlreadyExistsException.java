package com.example.armaria.errors;

public class ArmoryKeeperAlreadyExistsException extends RuntimeException {
  public ArmoryKeeperAlreadyExistsException(String registration) {
    super(registration);
  }
}