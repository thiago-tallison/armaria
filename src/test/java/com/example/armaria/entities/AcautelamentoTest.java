package com.example.armaria.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AcautelamentoTest {
  private MunicipalGuard gm;
  private Armeiro armeiro;
  private Equipamento equipamento;
  private ItemAcautelado itemAcautelado;
  private Acautelamento acautelamento;

  private int quantidadeAcautelada = 10;

  @BeforeEach
  public void setUp() {
    armeiro = new Armeiro("matricula", "nome", "email", "telefone", "login", "senha");
    gm = new MunicipalGuard("matricula", "nome", "email", "telefone");

    equipamento = new Equipamento("nome", "num-serie", true);

    acautelamento = new Acautelamento(LocalDateTime.now(), gm, armeiro);

    itemAcautelado = new ItemAcautelado(equipamento, quantidadeAcautelada);
  }

  @Test
  void testAdicionarEquipamento() {
    assertEquals(0, acautelamento.getTotalEquipamentosAcautelados());

    acautelamento.adicionarEquipamento(itemAcautelado);

    assertEquals(1, acautelamento.getTotalEquipamentosAcautelados());

    assertEquals(quantidadeAcautelada, acautelamento.getTotalUnidadesAcautelados());
  }

  @Test
  void testRemoverEquipamento() {
    acautelamento.adicionarEquipamento(itemAcautelado);

    acautelamento.removerEquipamento(itemAcautelado);

    assertEquals(0, acautelamento.getTotalEquipamentosAcautelados());
  }

  @Test
  @DisplayName("Deve ser possível remover um item acautelado em quantidade menor que a acutelada")
  void deve_ser_possivel_remover_quantidade_menor_que_acautelada() {
    int quantidadeASerRemovida = 1;

    acautelamento.adicionarEquipamento(itemAcautelado);
    acautelamento.diminuirQuantidade(itemAcautelado, quantidadeASerRemovida);

    assertEquals(quantidadeAcautelada - quantidadeASerRemovida,
        acautelamento.getItem(itemAcautelado).get().getQuantidadeAcautelada());

    assertEquals(1, acautelamento.getTotalEquipamentosAcautelados());
  }
}
