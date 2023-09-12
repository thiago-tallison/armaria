package com.example.armaria.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EstoqueTest {
  private Estoque estoque;
  private Equipament equipamentA;
  private Equipament equipamentB;

  @BeforeEach
  void setUp() {
    estoque = new Estoque();
    equipamentA = new Equipament("Equipament A", "serial-number-A", true);
    equipamentB = new Equipament("Equipament B", "serial-number-B", true);
  }

  @Test
  void testAdicionarItemEmEstoque() {
    estoque.adicionarItemEmEstoque(equipamentA, 10);
    estoque.adicionarItemEmEstoque(equipamentB, 5);

    assertEquals(10, estoque.getQuantityInStock(equipamentA));
    assertEquals(5, estoque.getQuantityInStock(equipamentB));
  }

  @Test
  void testRemoverItemDoEstoque() {
    estoque.adicionarItemEmEstoque(equipamentA, 10);
    estoque.adicionarItemEmEstoque(equipamentB, 5);

    estoque.removerQuantidade(equipamentA, 5);
    estoque.removerQuantidade(equipamentB, 3);

    assertEquals(5, estoque.getQuantityInStock(equipamentA));
    assertEquals(2, estoque.getQuantityInStock(equipamentB));
  }

  @Test
  void testRemoverItemDoEstoqueQuantidadeZero() {
    estoque.adicionarItemEmEstoque(equipamentA, 10);

    estoque.removerQuantidade(equipamentA, 10);

    assertEquals(0, estoque.getQuantityInStock(equipamentA));
  }

  @Test
  void testRemoverItemDoEstoqueInexistente() {
    estoque.adicionarItemEmEstoque(equipamentA, 10);

    estoque.removerQuantidade(equipamentB, 5);

    assertEquals(10, estoque.getQuantityInStock(equipamentA));
  }

  @Test
  void testAcautelarEquipaments() {
    estoque.adicionarItemEmEstoque(equipamentA, 10);
    estoque.adicionarItemEmEstoque(equipamentB, 5);

    List<CheckedoutItem> itensAcautelados = new ArrayList<>();

    itensAcautelados.add(new CheckedoutItem(equipamentA, 8));
    itensAcautelados.add(new CheckedoutItem(equipamentB, 4));

    estoque.checkOutEquipaments(itensAcautelados);

    assertEquals(2, estoque.getQuantityInStock(equipamentA));
    assertEquals(1, estoque.getQuantityInStock(equipamentB));
  }

  @Test
  void testReturnEquipaments() {
    estoque.adicionarItemEmEstoque(equipamentA, 10);

    Map<Equipament, Integer> returnedEquipaments = new HashMap<>();
    returnedEquipaments.put(equipamentA, 5);

    estoque.returnEquipaments(returnedEquipaments);

    assertEquals(15, estoque.getQuantityInStock(equipamentA));
  }
}
