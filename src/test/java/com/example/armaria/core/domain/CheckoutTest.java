package com.example.armaria.core.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CheckoutTest {
  private MunicipalGuard gm;
  private Armorer armorer;
  private Equipament equipament;
  private CheckedoutItem checkedoutItem;
  private Checkout checkout;

  private int checkedoutQuantity = 10;

  @BeforeEach
  public void setUp() {
    armorer = new Armorer("matricula", "nome", "email", "telefone", "login", "senha");
    gm = new MunicipalGuard("matricula", "nome", "email", "telefone");

    equipament = new Equipament("nome", "num-serie", true);

    checkout = new Checkout(LocalDateTime.now(), gm, armorer);

    checkedoutItem = new CheckedoutItem(equipament, checkedoutQuantity);
  }

  @Test
  void testAdicionarEquipament() {
    assertEquals(0, checkout.getItemsSize());

    checkout.addItem(checkedoutItem);

    assertEquals(1, checkout.getItemsSize());

    assertEquals(checkedoutQuantity, checkout.getTotalUnidadesAcautelados());
  }

  @Test
  void testRemoveEquipament() {
    checkout.addItem(checkedoutItem);

    checkout.removeItem(checkedoutItem);

    assertEquals(0, checkout.getItemsSize());
  }

  @Test
  @DisplayName("Deve ser possível remover um item acautelado em quantidade menor que a acutelada")
  void deve_ser_possivel_remover_quantidade_menor_que_acautelada() {
    int quantidadeASerRemovida = 1;

    checkout.addItem(checkedoutItem);
    checkout.decreaseQuantity(checkedoutItem, quantidadeASerRemovida);

    assertEquals(checkedoutQuantity - quantidadeASerRemovida,
        checkout.getItem(checkedoutItem).get().getCheckoutQuantity());

    assertEquals(1, checkout.getItemsSize());
  }
}
