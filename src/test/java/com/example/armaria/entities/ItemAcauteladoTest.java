package com.example.armaria.entities;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ItemAcauteladoTest {
  @Test
  void nao_deve_ser_possivel_acautelar_menos_de_um_item() {
    assertThrows(IllegalArgumentException.class, () -> new ItemAcautelado(new ItemEstoque(), 0));
  }

  @Test
  void deve_ser_possivel_acautelar_um_ou_mais_itens() {
    assertDoesNotThrow(() -> new ItemAcautelado(new ItemEstoque(), 1));
  }
}
