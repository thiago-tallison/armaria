package com.example.armaria.use_cases.checkout;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.armaria.dtos.checkout.CheckoutCreateTDO;
import com.example.armaria.entities.ArmoryKepper;
import com.example.armaria.entities.CheckedoutItem;
import com.example.armaria.entities.Checkout;
import com.example.armaria.entities.Equipament;
import com.example.armaria.entities.MunicipalGuard;
import com.example.armaria.errors.ArmoryKeeperNotFoundException;
import com.example.armaria.errors.MunicipalGuardNotFoundException;
import com.example.armaria.repositories.ArmoryKeeperRepository;
import com.example.armaria.repositories.CheckoutRepository;
import com.example.armaria.repositories.MunicipalGuardRepository;
import com.example.armaria.repositories.StockItemRepository;

import jakarta.transaction.Transactional;

@Service
public class CreateCheckoutEquipamentUseCase {
  private final CheckoutRepository checkoutRepository;
  private final StockItemRepository stockItemRepository;
  private final ArmoryKeeperRepository armoryKeeperRepository;
  private final MunicipalGuardRepository gmRepository;

  public CreateCheckoutEquipamentUseCase(
      CheckoutRepository checkoutRepository,
      StockItemRepository stockItemRepository,
      ArmoryKeeperRepository armoryKeeperRepository,
      MunicipalGuardRepository gmRepository) {
    this.checkoutRepository = checkoutRepository;
    this.stockItemRepository = stockItemRepository;
    this.armoryKeeperRepository = armoryKeeperRepository;
    this.gmRepository = gmRepository;
  }

  @Transactional
  public void execute(CheckoutCreateTDO data) {
    // check if armory keeper exists
    String armoryKeeperRegistration = data.armoryKeeperRegistration();
    Optional<ArmoryKepper> optionalArmoryKeeper = armoryKeeperRepository
        .findByRegistrationNumber(armoryKeeperRegistration);
    if (!optionalArmoryKeeper.isPresent()) {
      throw new ArmoryKeeperNotFoundException(armoryKeeperRegistration);
    }

    // check if municipal guard exists
    String registration = data.municipalGuardRegistration();
    Optional<MunicipalGuard> gmOptional = gmRepository
        .findByRegistrationNumber(registration);

    if (!gmOptional.isPresent()) {
      throw new MunicipalGuardNotFoundException(registration);
    }

    // verificar se cada item acautelado existe e se tem quantidade em estoque
    // suficiente
    List<CheckedOutItemDTO> itens = data.itens();
    List<CheckedoutItem> itensAcautelados = new ArrayList<>();

    for (int i = 0; i < itens.size(); i++) {
      Long idAtual = itens.get(i).id();
      Integer quantidadeASerAcautelada = itens.get(i).quantityCheckedOut();

      int linhasAfetadas = stockItemRepository
          .increaseStockItemQuantity(idAtual, quantidadeASerAcautelada);

      if (linhasAfetadas == 0) {
        throw new RuntimeException("Não foi possível acautelar o item " + idAtual);
      }

      Equipament equipament = new Equipament();
      equipament.setId(itens.get(i).equipamentId());

      itensAcautelados.add(new CheckedoutItem(equipament,
          quantidadeASerAcautelada));
    }

    Checkout checkout = new Checkout(null, data.checkoutDate(), gmOptional.get(),
        optionalArmoryKeeper.get());
    itensAcautelados.forEach(checkout::addItem);

    checkoutRepository.save(checkout);
  }
}
