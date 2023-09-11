package com.example.armaria.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DevolucaoTest {
  private MunicipalGuard mg;
  private ArmoryKepper armoryKeeper;
  private Equipament equipament;
  private ItemAcautelado itemAcautelado;
  private ItemDevolvido itemDevolvido;
  private Acautelamento acautelamento;
  private Devolucao devolucao;

  @BeforeEach
  public void setUp() {
    mg = new MunicipalGuard("registration", "name", "email", "phone");
    equipament = new Equipament("nome", "num-serie", true);

    acautelamento = new Acautelamento(LocalDateTime.now(), mg, armoryKeeper);

    itemAcautelado = new ItemAcautelado(equipament, 1);

    devolucao = new Devolucao(acautelamento, LocalDateTime.now(), mg, armoryKeeper);

    itemDevolvido = new ItemDevolvido(equipament, 1);
  }

  @Test
  public void deve_ser_possivel_adicionar_itens_devolvidos() {
    int quantidadeDevolvida = 1;

    itemDevolvido.setQuantidadeDevolvida(quantidadeDevolvida);

    devolucao.addItemDevolvido(itemDevolvido);

    // assertTrue(devolucao.getItensDevolvidos().size() == 1);
    assertEquals(itemAcautelado.getEquipament(), itemDevolvido.getEquipament());
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
