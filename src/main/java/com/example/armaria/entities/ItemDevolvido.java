package com.example.armaria.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "itens_devolvidos")
public class ItemDevolvido {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne
  @JoinColumn(name = "id_equipamento")
  private final Equipamento equipamento;

  @Column(name = "quantidade_devolvida")
  private int quantidadeDevolvida;

  @ManyToOne
  @JoinColumn(name = "id_acautelamento")
  private Acautelamento acautelamento;

  @ManyToOne
  private Devolucao devolucao;

  public ItemDevolvido(Equipamento equipamento, int quantidadeDevolvida) {
    this.equipamento = equipamento;
    setQuantidadeDevolvida(quantidadeDevolvida);
  }

  public void setQuantidadeDevolvida(int quantidadeDevolvida) {
    if (quantidadeDevolvida <= 0) {
      throw new IllegalArgumentException("Não é possível devolver um item em quantidade menor ou igual a zero");
    }

    this.quantidadeDevolvida = quantidadeDevolvida;
  }

}
