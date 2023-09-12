package com.example.armaria.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CheckoutTest {
  private MunicipalGuard gm;
  private ArmoryKepper armoryKeeper;
  private Equipament equipament;
  private ItemAcautelado itemAcautelado;
  private Checkout checkout;

  private int quantidadeAcautelada = 10;

  @BeforeEach
  public void setUp() {
    armoryKeeper = new ArmoryKepper("matricula", "nome", "email", "telefone", "login", "senha");
    gm = new MunicipalGuard("matricula", "nome", "email", "telefone");

    equipament = new Equipament("nome", "num-serie", true);

    checkout = new Checkout(LocalDateTime.now(), gm, armoryKeeper);

    itemAcautelado = new ItemAcautelado(equipament, quantidadeAcautelada);
  }

  @Test
  void testAdicionarEquipament() {
    assertEquals(0, checkout.getItemsSize());

    checkout.addItem(itemAcautelado);

    assertEquals(1, checkout.getItemsSize());

    assertEquals(quantidadeAcautelada, checkout.getTotalUnidadesAcautelados());
  }

  @Test
  void testRemoveEquipament() {
    checkout.addItem(itemAcautelado);

    checkout.removeItem(itemAcautelado);

    assertEquals(0, checkout.getItemsSize());
  }

  @Test
  @DisplayName("Deve ser possível remover um item acautelado em quantidade menor que a acutelada")
  void deve_ser_possivel_remover_quantidade_menor_que_acautelada() {
    int quantidadeASerRemovida = 1;

    checkout.addItem(itemAcautelado);
    checkout.decreaseQuantity(itemAcautelado, quantidadeASerRemovida);

    assertEquals(quantidadeAcautelada - quantidadeASerRemovida,
        checkout.getItem(itemAcautelado).get().getCheckoutQuantity());

    assertEquals(1, checkout.getItemsSize());
  }
}
