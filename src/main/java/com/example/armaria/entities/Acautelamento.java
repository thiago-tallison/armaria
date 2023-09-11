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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "acautelamentos")
public class Acautelamento {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "data_acautelamento")
  @NonNull
  private LocalDateTime dataAcautelamento;

  @ManyToOne
  @JoinColumn(name = "municipal_guard_registration")
  @NonNull
  private MunicipalGuard guard;

  @ManyToOne
  @JoinColumn(name = "armory_keeper_registration_number")
  @NonNull
  private ArmoryKepper armoryKeeper;

  @OneToMany(mappedBy = "acautelamento", cascade = CascadeType.ALL)
  @Setter(AccessLevel.NONE)
  private final List<ItemAcautelado> itensAcautelados = new ArrayList<>();

  public Acautelamento(
      LocalDateTime dataAcautelamento,
      MunicipalGuard guard,
      ArmoryKepper armoryKeeper) {
    this.dataAcautelamento = dataAcautelamento;
    this.guard = guard;
    this.armoryKeeper = armoryKeeper;
  }

  public void addItemAcautelado(ItemAcautelado item) {
    itensAcautelados.add(item);
  }

  public void removeItemAcautelado(ItemAcautelado item) {
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
      removeItemAcautelado(item);
      return;
    }

    quantidadeAtual -= quantidade;

    itensAcautelados.get(index).setQuantidadeAcautelada(quantidadeAtual);
  }

  public int getTotalEquipamentsAcautelados() {
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
