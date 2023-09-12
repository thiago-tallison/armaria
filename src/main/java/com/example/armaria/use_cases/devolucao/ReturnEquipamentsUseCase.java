package com.example.armaria.use_cases.devolucao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.armaria.dtos.devolucao.EquipamentReturnDTO;
import com.example.armaria.dtos.devolucao.ItemDevolvidoDTO;
import com.example.armaria.entities.ArmoryKepper;
import com.example.armaria.entities.Checkout;
import com.example.armaria.entities.Devolucao;
import com.example.armaria.entities.ItemDevolvido;
import com.example.armaria.entities.ItemEstoque;
import com.example.armaria.entities.MunicipalGuard;
import com.example.armaria.errors.CheckoutNotFoundException;
import com.example.armaria.repositories.CheckoutRepository;
import com.example.armaria.repositories.ItemEstoqueRepository;
import com.example.armaria.use_cases.armory_keeper.GetArmoryKeeperByRegistrationUseCase;
import com.example.armaria.use_cases.municipal_guard.GetMunicipalGuardByRegistrationUseCase;

import jakarta.transaction.Transactional;

@Service
public class ReturnEquipamentsUseCase {
  private final CheckoutRepository checkoutRepository;
  private final ReturnEquipamentRepository returnEquipamentRepository;
  private final ItemEstoqueRepository itemEstoqueRepository;
  private final GetArmoryKeeperByRegistrationUseCase getArmoryKeeperByRegistrationUseCase;
  private final GetMunicipalGuardByRegistrationUseCase getMunicipalGuardByRegistrationUseCase;

  public ReturnEquipamentsUseCase(
      CheckoutRepository checkoutRepository,
      ReturnEquipamentRepository returnEquipamentRepository,
      ItemEstoqueRepository itemEstoqueRepository,
      GetArmoryKeeperByRegistrationUseCase getArmoryKeeperByRegistrationUseCase,
      GetMunicipalGuardByRegistrationUseCase getMunicipalGuardByRegistrationUseCase) {
    this.checkoutRepository = checkoutRepository;
    this.itemEstoqueRepository = itemEstoqueRepository;
    this.returnEquipamentRepository = returnEquipamentRepository;
    this.getArmoryKeeperByRegistrationUseCase = getArmoryKeeperByRegistrationUseCase;
    this.getMunicipalGuardByRegistrationUseCase = getMunicipalGuardByRegistrationUseCase;
  }

  @Transactional
  public void execute(EquipamentReturnDTO equipamentReturnDTO) {
    // verificar se gm existe
    String armoryKeeperRegistrationNumber = equipamentReturnDTO.armoryKeeperRegistration();
    Optional<ArmoryKepper> armoryKeeper = getArmoryKeeperByRegistrationUseCase
        .execute(armoryKeeperRegistrationNumber);

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
      int linhasAfetadas = itemEstoqueRepository.aumentarQuantidadeEmEstoque(item.idItemEstoque(),
          item.quantidadeDevolvida());

      if (linhasAfetadas == 0) {
        throw new QuantidadeEmEstoqueNaoAumentadaException(item.idItemEstoque());
      }

      // save equipament return
      ItemEstoque itemEstoque = new ItemEstoque();
      itemEstoque.setId(item.idItemEstoque());

      ItemDevolvido itemDevolvido = new ItemDevolvido();
      itemDevolvido.setItemEstoque(itemEstoque);
      itemDevolvido.setQuantidadeDevolvida(item.quantidadeDevolvida());
      itemDevolvido.setCheckout(checkout);

      itensDevolvidos.add(itemDevolvido);
    }

    Devolucao devolucao = new Devolucao(checkout, LocalDateTime.now(), gmOptional.get(), armoryKeeper.get());
    itensDevolvidos.forEach(devolucao::addItemDevolvido);
    returnEquipamentRepository.save(devolucao);
  }
}
