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
@Table(name = "checkouts")
public class Checkout {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "checkout_date")
  @NonNull
  private LocalDateTime checkoutDate;

  @ManyToOne
  @JoinColumn(name = "municipal_guard_registration")
  @NonNull
  private MunicipalGuard guard;

  @ManyToOne
  @JoinColumn(name = "armory_keeper_registration_number")
  @NonNull
  private ArmoryKepper armoryKeeper;

  @OneToMany(mappedBy = "checkout", cascade = CascadeType.ALL)
  @Setter(AccessLevel.NONE)
  private final List<CheckedoutItem> checkedOutItems = new ArrayList<>();

  public Checkout(
      LocalDateTime checkoutDate,
      MunicipalGuard guard,
      ArmoryKepper armoryKeeper) {
    this.checkoutDate = checkoutDate;
    this.guard = guard;
    this.armoryKeeper = armoryKeeper;
  }

  public void addItem(CheckedoutItem item) {
    checkedOutItems.add(item);
  }

  public void removeItem(CheckedoutItem item) {
    checkedOutItems.remove(item);
  }

  public void adicionarQuantidade(CheckedoutItem item, int quantity) {
    int index = checkedOutItems.indexOf(item);

    if (index != -1) {
      int currentQuantity = checkedOutItems.get(index).getCheckoutQuantity();
      currentQuantity += item.getCheckoutQuantity();
      checkedOutItems.get(index).setCheckoutQuantity(currentQuantity);
    }
  }

  public void decreaseQuantity(CheckedoutItem item, int quantity) {
    int index = checkedOutItems.indexOf(item);

    if (index == -1) {
      return;
    }

    int currentQuantity = checkedOutItems.get(index).getCheckoutQuantity();

    if (currentQuantity <= quantity) {
      removeItem(item);
      return;
    }

    currentQuantity -= quantity;

    checkedOutItems.get(index).setCheckoutQuantity(currentQuantity);
  }

  public int getItemsSize() {
    return checkedOutItems.size();
  }

  public int getTotalUnidadesAcautelados() {
    return checkedOutItems
        .stream()
        .mapToInt(CheckedoutItem::getCheckoutQuantity)
        .sum();
  }

  public Optional<CheckedoutItem> getItem(CheckedoutItem item) {
    int index = checkedOutItems.indexOf(item);

    if (index == -1) {
      return null;
    }

    return Optional.ofNullable(checkedOutItems.get(index));
  }

}
