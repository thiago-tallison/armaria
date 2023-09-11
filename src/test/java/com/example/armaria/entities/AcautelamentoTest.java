package com.example.armaria.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AcautelamentoTest {
  private MunicipalGuard gm;
  private ArmoryKepper armoryKeeper;
  private Equipament equipament;
  private ItemAcautelado itemAcautelado;
  private Acautelamento acautelamento;

  private int quantidadeAcautelada = 10;

  @BeforeEach
  public void setUp() {
    armoryKeeper = new ArmoryKepper("matricula", "nome", "email", "telefone", "login", "senha");
    gm = new MunicipalGuard("matricula", "nome", "email", "telefone");

    equipament = new Equipament("nome", "num-serie", true);

    acautelamento = new Acautelamento(LocalDateTime.now(), gm, armoryKeeper);

    itemAcautelado = new ItemAcautelado(equipament, quantidadeAcautelada);
  }

  @Test
  void testAdicionarEquipament() {
    assertEquals(0, acautelamento.getTotalEquipamentsAcautelados());

    acautelamento.addItemAcautelado(itemAcautelado);

    assertEquals(1, acautelamento.getTotalEquipamentsAcautelados());

    assertEquals(quantidadeAcautelada, acautelamento.getTotalUnidadesAcautelados());
  }

  @Test
  void testRemoveEquipament() {
    acautelamento.addItemAcautelado(itemAcautelado);

    acautelamento.removeItemAcautelado(itemAcautelado);

    assertEquals(0, acautelamento.getTotalEquipamentsAcautelados());
  }

  @Test
  @DisplayName("Deve ser possível remover um item acautelado em quantidade menor que a acutelada")
  void deve_ser_possivel_remover_quantidade_menor_que_acautelada() {
    int quantidadeASerRemovida = 1;

    acautelamento.addItemAcautelado(itemAcautelado);
    acautelamento.diminuirQuantidade(itemAcautelado, quantidadeASerRemovida);

    assertEquals(quantidadeAcautelada - quantidadeASerRemovida,
        acautelamento.getItem(itemAcautelado).get().getQuantidadeAcautelada());

    assertEquals(1, acautelamento.getTotalEquipamentsAcautelados());
  }
}
