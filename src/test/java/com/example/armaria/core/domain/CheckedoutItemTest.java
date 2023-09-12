package com.example.armaria.core.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class CheckedoutItemTest {
  @Test
  void itShouldNotBeAbleToCheckoutLessThanOneItem() {
    assertThrows(IllegalArgumentException.class, () -> new CheckedoutItem(new StockItem(), 0));
  }

  @Test
  void itShouldBeAbleToCheckoutMoreThanZeroItems() {
    assertDoesNotThrow(() -> new CheckedoutItem(new StockItem(), 1));
  }
}
