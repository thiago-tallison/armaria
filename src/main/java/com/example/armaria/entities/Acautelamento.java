package com.example.armaria.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

@Data
@Entity
@Table(name = "acautelamentos")
public class Acautelamento {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "data_acautelamento")
  private final LocalDateTime dataAcautelamento;

  @ManyToOne
  @JoinColumn(name = "matricula_gm")
  private final GuardaMunicipal gm;

  @ManyToOne
  @JoinColumn(name = "matricula_armeiro")
  private final Armeiro armeiro;

  @OneToMany(mappedBy = "acautelamento", cascade = CascadeType.ALL)
  @Getter(AccessLevel.NONE)
  private List<ItemAcautelado> itensAcautelados = new ArrayList<>();

  public void adicionarEquipamento(ItemAcautelado item) {
    itensAcautelados.add(item);
  }

  public void removerEquipamento(ItemAcautelado item) {
    itensAcautelados.remove(item);
  }

  public void adicionarQuantidade(ItemAcautelado item, int quantidade) {
    int index = itensAcautelados.indexOf(item);

    if (index != -1) {
      int quantidadeAtual = itensAcautelados.get(index).getQuantidadeAcautelada();
      quantidadeAtual += item.getQuantidadeAcautelada();
      itensAcautelados.get(index).setQuantidadeAcautelada(quantidadeAtual);
    }
  }

  public void diminuirQuantidade(ItemAcautelado item, int quantidade) {
    int index = itensAcautelados.indexOf(item);

    if (index == -1) {
      return;
    }

    int quantidadeAtual = itensAcautelados.get(index).getQuantidadeAcautelada();

    if (quantidadeAtual <= quantidade) {
      removerEquipamento(item);
      return;
    }

    quantidadeAtual -= quantidade;

    itensAcautelados.get(index).setQuantidadeAcautelada(quantidadeAtual);
  }

  public int getTotalEquipamentosAcautelados() {
    return itensAcautelados.size();
  }

  public int getTotalUnidadesAcautelados() {
    return itensAcautelados
        .stream()
        .mapToInt(ItemAcautelado::getQuantidadeAcautelada)
        .sum();
  }

  public Optional<ItemAcautelado> getItem(ItemAcautelado item) {
    int index = itensAcautelados.indexOf(item);

    if (index == -1) {
      return null;
    }

    return Optional.ofNullable(itensAcautelados.get(index));
  }

}
