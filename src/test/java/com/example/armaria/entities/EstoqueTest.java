package com.example.armaria.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EstoqueTest {
  private Estoque estoque;
  private Equipamento equipamentoA;
  private Equipamento equipamentoB;

  @BeforeEach
  void setUp() {
    estoque = new Estoque();
    equipamentoA = new Equipamento("Equipamento A", "num-serie-A", true);
    equipamentoB = new Equipamento("Equipamento B", "num-serie-B", true);
  }

  @Test
  void testAdicionarItemEmEstoque() {
    estoque.adicionarItemEmEstoque(equipamentoA, 10);
    estoque.adicionarItemEmEstoque(equipamentoB, 5);

    assertEquals(10, estoque.getQuantidadeEmEstoque(equipamentoA));
    assertEquals(5, estoque.getQuantidadeEmEstoque(equipamentoB));
  }

  @Test
  void testRemoverItemDoEstoque() {
    estoque.adicionarItemEmEstoque(equipamentoA, 10);
    estoque.adicionarItemEmEstoque(equipamentoB, 5);

    estoque.removerItemDoEstoque(equipamentoA, 5);
    estoque.removerItemDoEstoque(equipamentoB, 3);

    assertEquals(5, estoque.getQuantidadeEmEstoque(equipamentoA));
    assertEquals(2, estoque.getQuantidadeEmEstoque(equipamentoB));
  }

  @Test
  void testRemoverItemDoEstoqueQuantidadeZero() {
    estoque.adicionarItemEmEstoque(equipamentoA, 10);

    estoque.removerItemDoEstoque(equipamentoA, 10);

    assertEquals(0, estoque.getQuantidadeEmEstoque(equipamentoA));
  }

  @Test
  void testRemoverItemDoEstoqueInexistente() {
    estoque.adicionarItemEmEstoque(equipamentoA, 10);

    estoque.removerItemDoEstoque(equipamentoB, 5);

    assertEquals(10, estoque.getQuantidadeEmEstoque(equipamentoA));
  }

  @Test
  void testAcautelarEquipamentos() {
    estoque.adicionarItemEmEstoque(equipamentoA, 10);
    estoque.adicionarItemEmEstoque(equipamentoB, 5);

    Map<Equipamento, Integer> equipamentosAcautelados = new HashMap<>();
    equipamentosAcautelados.put(equipamentoA, 8);
    equipamentosAcautelados.put(equipamentoB, 4);

    estoque.acautelarEquipamentos(equipamentosAcautelados);

    assertEquals(2, estoque.getQuantidadeEmEstoque(equipamentoA));
    assertEquals(1, estoque.getQuantidadeEmEstoque(equipamentoB));
  }

  @Test
  void testDevolverEquipamentos() {
    estoque.adicionarItemEmEstoque(equipamentoA, 10);

    Map<Equipamento, Integer> equipamentosDevolvidos = new HashMap<>();
    equipamentosDevolvidos.put(equipamentoA, 5);

    estoque.devolverEquipamentos(equipamentosDevolvidos);

    assertEquals(15, estoque.getQuantidadeEmEstoque(equipamentoA));
  }
}
