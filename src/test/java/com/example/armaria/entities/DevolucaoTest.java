package com.example.armaria.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DevolucaoTest {
  private MunicipalGuard gm;
  private Armeiro armeiro;
  private Equipamento equipamento;
  private ItemAcautelado itemAcautelado;
  private ItemDevolvido itemDevolvido;
  private Acautelamento acautelamento;
  private Devolucao devolucao;

  @BeforeEach
  public void setUp() {
    gm = new MunicipalGuard("matricula", "nome", "email", "telefone");
    equipamento = new Equipamento("nome", "num-serie", true);

    acautelamento = new Acautelamento(LocalDateTime.now(), gm, armeiro);

    itemAcautelado = new ItemAcautelado(equipamento, 1);

    devolucao = new Devolucao(acautelamento, LocalDateTime.now(), gm, armeiro);

    itemDevolvido = new ItemDevolvido(equipamento, 1);
  }

  @Test
  public void deve_ser_possivel_adicionar_itens_devolvidos() {
    int quantidadeDevolvida = 1;

    itemDevolvido.setQuantidadeDevolvida(quantidadeDevolvida);

    devolucao.adicionarEquipamento(itemDevolvido);

    // assertTrue(devolucao.getItensDevolvidos().size() == 1);
    assertEquals(itemAcautelado.getEquipamento(), itemDevolvido.getEquipamento());
  }

  @Test
  public void deve_ser_possivel_remover_itens_devolvidos() {
    int quantidadeDevolvida = 5;
    int quantidadeASerRemovida = 1;

    itemDevolvido.setQuantidadeDevolvida(quantidadeDevolvida);

    devolucao.adicionarEquipamento(itemDevolvido);
    devolucao.removerEquipamento(itemDevolvido, quantidadeASerRemovida);

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

    devolucao.adicionarEquipamento(itemDevolvido);
    devolucao.removerEquipamento(itemDevolvido, quantidadeMaiorQueQuantidadeDevolvida);

    // assertTrue(devolucao.getItensDevolvidos().isEmpty());
  }

}
