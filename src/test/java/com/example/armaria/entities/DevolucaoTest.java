package com.example.armaria.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DevolucaoTest {
  private MunicipalGuard mg;
  private Armorer armorer;
  private Equipament equipament;
  private CheckedoutItem checkedoutItem;
  private ItemDevolvido itemDevolvido;
  private Checkout checkout;
  private Devolucao devolucao;

  @BeforeEach
  public void setUp() {
    mg = new MunicipalGuard("registration", "name", "email", "phone");
    equipament = new Equipament("nome", "num-serie", true);

    checkout = new Checkout(LocalDateTime.now(), mg, armorer);

    checkedoutItem = new CheckedoutItem(equipament, 1);

    devolucao = new Devolucao(checkout, LocalDateTime.now(), mg, armorer);

    itemDevolvido = new ItemDevolvido(equipament, 1);
  }

  @Test
  public void deve_ser_possivel_adicionar_itens_devolvidos() {
    int quantidadeDevolvida = 1;

    itemDevolvido.setQuantidadeDevolvida(quantidadeDevolvida);

    devolucao.addItemDevolvido(itemDevolvido);

    // assertTrue(devolucao.getItensDevolvidos().size() == 1);
    assertEquals(checkedoutItem.getEquipament(), itemDevolvido.getEquipament());
  }

  @Test
  public void deve_ser_possivel_remover_itens_devolvidos() {
    int quantidadeDevolvida = 5;
    int quantidadeASerRemovida = 1;

    itemDevolvido.setQuantidadeDevolvida(quantidadeDevolvida);

    devolucao.addItemDevolvido(itemDevolvido);
    devolucao.removeItemDevolvido(itemDevolvido, quantidadeASerRemovida);

    // // int index = devolucao.getItensDevolvidos().indexOf(itemDevolvido);
    // // ItemDevolvido item = devolucao.getItensDevolvidos().get(index);

    // assertEquals(quantidadeDevolvida - quantidadeASerRemovida,
    // item.getQuantidadeDevolvida());
  }

  @Test
  public void deve_ser_possivel_remover_todos_itens_devolvidos() {
    int quantidadeDevolvida = 5;
    int quantidadeMaiorQueQuantidadeDevolvida = quantidadeDevolvida + 1;

    itemDevolvido.setQuantidadeDevolvida(quantidadeDevolvida);

    devolucao.addItemDevolvido(itemDevolvido);
    devolucao.removeItemDevolvido(itemDevolvido, quantidadeMaiorQueQuantidadeDevolvida);

    // assertTrue(devolucao.getItensDevolvidos().isEmpty());
  }

}
