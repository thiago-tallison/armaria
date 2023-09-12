package com.example.armaria.core.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

@Data
@Entity
@Table(name = "devolucoes")
public class Devolucao {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne
  @JoinColumn(name = "checkout_id")
  private final Checkout checkout;

  @Column(name = "data_devolucao")
  private final LocalDateTime dataDevolucao;

  @ManyToOne
  @JoinColumn(name = "matricula_gm")
  private final MunicipalGuard gm;

  @ManyToOne
  @JoinColumn(name = "armorer_registration")
  private final Armorer armorer;

  @OneToMany(mappedBy = "devolucao", cascade = CascadeType.ALL)
  @Getter(AccessLevel.NONE)
  private final List<ItemDevolvido> itensDevolvidos = new ArrayList<>();

  public void addItemDevolvido(ItemDevolvido itemDevolvido) {
    itensDevolvidos.add(itemDevolvido);
  }

  public void removeItemDevolvido(ItemDevolvido itemDevolvido, int quantity) {
    int index = itensDevolvidos.indexOf(itemDevolvido);
    if (index == -1) {
      return;
    }

    ItemDevolvido item = itensDevolvidos.get(index);

    if (quantity >= item.getQuantidadeDevolvida()) {
      itensDevolvidos.remove(index);
    } else {
      itemDevolvido.setQuantidadeDevolvida(item.getQuantidadeDevolvida() - quantity);
      // Atualiza a quantidade do item que esta sendo devolvido
      itensDevolvidos.set(index, item);
    }
  }

}
