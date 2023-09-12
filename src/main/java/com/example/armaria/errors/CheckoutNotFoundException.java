package com.example.armaria.errors;

public class CheckoutNotFoundException extends RuntimeException {
  public CheckoutNotFoundException(Long checkoutId) {
    super("Acautelamento com id " + checkoutId + " não encontrado");
  }

}
