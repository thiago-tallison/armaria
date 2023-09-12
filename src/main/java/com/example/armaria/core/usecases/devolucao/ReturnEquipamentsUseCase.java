package com.example.armaria.core.usecases.devolucao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.armaria.core.domain.Armorer;
import com.example.armaria.core.domain.Checkout;
import com.example.armaria.core.domain.Devolucao;
import com.example.armaria.core.domain.ItemDevolvido;
import com.example.armaria.core.domain.MunicipalGuard;
import com.example.armaria.core.domain.StockItem;
import com.example.armaria.core.usecases.armorer.GetArmorerByRegistrationUseCase;
import com.example.armaria.core.usecases.municipal_guard.GetMunicipalGuardByRegistrationUseCase;
import com.example.armaria.dtos.devolucao.EquipamentReturnDTO;
import com.example.armaria.dtos.devolucao.ItemDevolvidoDTO;
import com.example.armaria.errors.CheckoutNotFoundException;
import com.example.armaria.repositories.CheckoutRepository;
import com.example.armaria.repositories.StockItemRepository;

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
