package com.example.armaria.core.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ItemDevolvidoTest {
  @Test
  void nao_deve_ser_possivel_devolver_menos_de_um_item() {
    assertThrows(IllegalArgumentException.class, () -> new ItemDevolvido(null, 0));
  }

  @Test
  void deve_ser_possivel_devolver_um_item() {
    assertDoesNotThrow(() -> new ItemDevolvido(null, 1));
  }
}
