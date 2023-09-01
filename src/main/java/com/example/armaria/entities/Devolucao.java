package com.example.armaria.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Devolucao {
  private Long id;
  private final Acautelamento acautelamento;
  private final LocalDateTime dataDevolucao;
  private final GuardaMunicipal gm;
  private final Armeiro armeiro;
  private final List<ItemDevolvido> itensDevolvidos = new ArrayList<>();

  public void adicionarEquipamento(ItemDevolvido itemDevolvido) {
    itensDevolvidos.add(itemDevolvido);
  }

  public void removerEquipamento(ItemDevolvido itemDevolvido, int quantidade) {
    int index = itensDevolvidos.indexOf(itemDevolvido);
    if (index == -1) {
      return;
    }

    ItemDevolvido item = itensDevolvidos.get(index);

    if (quantidade >= item.getQuantidadeDevolvida()) {
      itensDevolvidos.remove(index);
    } else {
      itemDevolvido.setQuantidadeDevolvida(item.getQuantidadeDevolvida() - quantidade);
      // Atualiza a quantidade do item que esta sendo devolvido
      itensDevolvidos.set(index, item);
    }
  }

}
