package com.example.armaria.use_cases.devolucao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.armaria.dtos.devolucao.EquipamentReturnDTO;
import com.example.armaria.dtos.devolucao.ItemDevolvidoDTO;
import com.example.armaria.entities.Armorer;
import com.example.armaria.entities.Checkout;
import com.example.armaria.entities.Devolucao;
import com.example.armaria.entities.ItemDevolvido;
import com.example.armaria.entities.MunicipalGuard;
import com.example.armaria.entities.StockItem;
import com.example.armaria.errors.CheckoutNotFoundException;
import com.example.armaria.repositories.CheckoutRepository;
import com.example.armaria.repositories.StockItemRepository;
import com.example.armaria.use_cases.armorer.GetArmorerByRegistrationUseCase;
import com.example.armaria.use_cases.municipal_guard.GetMunicipalGuardByRegistrationUseCase;

import jakarta.transaction.Transactional;

@Service
public class ReturnEquipamentsUseCase {
  private final CheckoutRepository checkoutRepository;
  private final ReturnEquipamentRepository returnEquipamentRepository;
  private final StockItemRepository stockItemRepository;
  private final GetArmorerByRegistrationUseCase getArmorerByRegistrationUseCase;
  private final GetMunicipalGuardByRegistrationUseCase getMunicipalGuardByRegistrationUseCase;

  public ReturnEquipamentsUseCase(
      CheckoutRepository checkoutRepository,
      ReturnEquipamentRepository returnEquipamentRepository,
      StockItemRepository stockItemRepository,
      GetArmorerByRegistrationUseCase getArmorerByRegistrationUseCase,
      GetMunicipalGuardByRegistrationUseCase getMunicipalGuardByRegistrationUseCase) {
    this.checkoutRepository = checkoutRepository;
    this.stockItemRepository = stockItemRepository;
    this.returnEquipamentRepository = returnEquipamentRepository;
    this.getArmorerByRegistrationUseCase = getArmorerByRegistrationUseCase;
    this.getMunicipalGuardByRegistrationUseCase = getMunicipalGuardByRegistrationUseCase;
  }

  @Transactional
  public void execute(EquipamentReturnDTO equipamentReturnDTO) {
    // verificar se gm existe
    String armorerRegistration = equipamentReturnDTO.armorerRegistration();
    Optional<Armorer> optionalArmorer = getArmorerByRegistrationUseCase
        .execute(armorerRegistration);

    // check if municipal guard exists
    String registration = equipamentReturnDTO.municipalGuardRegistration();
    Optional<MunicipalGuard> gmOptional = getMunicipalGuardByRegistrationUseCase.execute(registration);

    // check if custody exists
    Long checkoutId = equipamentReturnDTO.checkoutId();
    Optional<Checkout> optionalCheckout = checkoutRepository.findById(checkoutId);
    if (!optionalCheckout.isPresent()) {
      throw new CheckoutNotFoundException(checkoutId);
    }

    // increase stock item quantity
    Checkout checkout = optionalCheckout.get();
    List<ItemDevolvido> itensDevolvidos = new ArrayList<>();
    for (ItemDevolvidoDTO item : equipamentReturnDTO.itensDevolvidos()) {
      int linhasAfetadas = stockItemRepository.decreaseStockItemQuantity(item.idItemEstoque(),
          item.quantidadeDevolvida());

      if (linhasAfetadas == 0) {
        throw new QuantidadeEmEstoqueNaoAumentadaException(item.idItemEstoque());
      }

      // save equipament return
      StockItem stockItem = new StockItem();
      stockItem.setId(item.idItemEstoque());

      ItemDevolvido itemDevolvido = new ItemDevolvido();
      itemDevolvido.setStockItem(stockItem);
      itemDevolvido.setQuantidadeDevolvida(item.quantidadeDevolvida());
      itemDevolvido.setCheckout(checkout);

      itensDevolvidos.add(itemDevolvido);
    }

    Devolucao devolucao = new Devolucao(checkout, LocalDateTime.now(), gmOptional.get(), optionalArmorer.get());
    itensDevolvidos.forEach(devolucao::addItemDevolvido);
    returnEquipamentRepository.save(devolucao);
  }
}