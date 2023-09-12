package com.example.armaria.entities;

import com.example.armaria.dtos.devolucao.ItemDevolvidoDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "itens_devolvidos")
public class ItemDevolvido {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne
  @JoinColumn(name = "equipamet_id")
  @NonNull
  private Equipament equipament;

  @OneToOne
  @JoinColumn(name = "stock_item_id")
  private ItemEstoque itemEstoque;

  @Column(name = "quantidade_devolvida")
  private int quantidadeDevolvida;

  @ManyToOne
  @JoinColumn(name = "id_acautelamento")
  private Acautelamento acautelamento;

  @ManyToOne
  private Devolucao devolucao;

  public ItemDevolvido(Equipament equipament, ItemEstoque itemEstoque, int quantidadeDevolvida) {
    this.equipament = equipament;
    this.itemEstoque = itemEstoque;
    this.quantidadeDevolvida = quantidadeDevolvida;
  }

  public ItemDevolvido(Equipament equipament, int quantidadeDevolvida) {
    this.equipament = equipament;
    setQuantidadeDevolvida(quantidadeDevolvida);
  }

  public ItemDevolvido(ItemDevolvidoDTO itemDevolvidoDTO) {
    this.quantidadeDevolvida = itemDevolvidoDTO.quantidadeDevolvida();
    this.itemEstoque = new ItemEstoque(itemDevolvidoDTO.idItemEstoque());
  }

  public void setQuantidadeDevolvida(int quantidadeDevolvida) {
    if (quantidadeDevolvida <= 0) {
      throw new IllegalArgumentException("Não é possível devolver um item em quantidade menor ou igual a zero");
    }

    this.quantidadeDevolvida = quantidadeDevolvida;
  }

}
