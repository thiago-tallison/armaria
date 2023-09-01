package com.example.armaria.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AcautelamentoTest {
  private GuardaMunicipal gm;
  private Armeiro armeiro;
  private Equipamento equipamento;
  private ItemAcautelado itemAcautelado;
  private Acautelamento acautelamento;

  private int quantidadeAcautelada = 10;

  @BeforeEach
  public void setUp() {
    armeiro = new Armeiro("matricula", "nome", "email", "telefone", "login", "senha");
    gm = new GuardaMunicipal("matricula", "nome", "email", "telefone");

    equipamento = new Equipamento("nome", "num-serie", true);

    acautelamento = new Acautelamento(LocalDateTime.now(), gm, armeiro);

    itemAcautelado = new ItemAcautelado(equipamento, quantidadeAcautelada);
  }

  @Test
  void testAdicionarEquipamento() {
    assertTrue(acautelamento.getEquipamentos().isEmpty());

    acautelamento.adicionarEquipamento(itemAcautelado);

    assertTrue(acautelamento.getEquipamentos().size() == 1);
    assertTrue(acautelamento.getEquipamentos().containsKey(itemAcautelado));
    assertEquals(acautelamento.getEquipamentos().get(itemAcautelado), quantidadeAcautelada);
  }

  @Test
  void testRemoverEquipamento() {
    acautelamento.adicionarEquipamento(itemAcautelado);
    acautelamento.removerEquipamento(itemAcautelado, itemAcautelado.getQuantidadeAcautelada());

    assertTrue(acautelamento.getEquipamentos().size() == 0);
    assertTrue(acautelamento.getEquipamentos().containsKey(itemAcautelado) == false);
    assertNotEquals(acautelamento.getEquipamentos().get(itemAcautelado), quantidadeAcautelada);

    assertTrue(acautelamento.getEquipamentos().isEmpty());
  }

  @Test
  @DisplayName("Deve ser possível remover um item acautelado em quantidade menor que a acutelada")
  void deve_ser_possivel_remover_quantidade_menor_que_acautelada() {
    int quantidadeASerRemovida = 1;

    acautelamento.adicionarEquipamento(itemAcautelado);
    acautelamento.removerEquipamento(itemAcautelado, quantidadeASerRemovida);

    assertEquals(acautelamento.getEquipamentos().get(itemAcautelado), quantidadeAcautelada - quantidadeASerRemovida);
    assertTrue(acautelamento.getEquipamentos().isEmpty() == false);
  }
}
