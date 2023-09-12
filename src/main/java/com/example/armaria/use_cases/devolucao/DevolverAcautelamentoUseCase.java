package com.example.armaria.use_cases.devolucao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.armaria.dtos.devolucao.EquipamentReturnDTO;
import com.example.armaria.dtos.devolucao.ItemDevolvidoDTO;
import com.example.armaria.entities.Acautelamento;
import com.example.armaria.entities.ArmoryKepper;
import com.example.armaria.entities.Devolucao;
import com.example.armaria.entities.ItemDevolvido;
import com.example.armaria.entities.ItemEstoque;
import com.example.armaria.entities.MunicipalGuard;
import com.example.armaria.errors.AcautelamentoNaoEncontradoException;
import com.example.armaria.repositories.AcautelamentoRepository;
import com.example.armaria.repositories.ItemEstoqueRepository;
import com.example.armaria.use_cases.armory_keeper.GetArmoryKeeperByRegistrationUseCase;
import com.example.armaria.use_cases.municipal_guard.GetMunicipalGuardByRegistrationUseCase;

import jakarta.transaction.Transactional;

@Service
public class DevolverAcautelamentoUseCase {
  private final AcautelamentoRepository acautelamentoRepository;
  private final ReturnEquipamentRepository returnEquipamentRepository;
  private final ItemEstoqueRepository itemEstoqueRepository;
  private final GetArmoryKeeperByRegistrationUseCase getArmoryKeeperByRegistrationUseCase;
  private final GetMunicipalGuardByRegistrationUseCase getMunicipalGuardByRegistrationUseCase;

  public DevolverAcautelamentoUseCase(
      AcautelamentoRepository acautelamentoRepository,
      ReturnEquipamentRepository returnEquipamentRepository,
      ItemEstoqueRepository itemEstoqueRepository,
      GetArmoryKeeperByRegistrationUseCase getArmoryKeeperByRegistrationUseCase,
      GetMunicipalGuardByRegistrationUseCase getMunicipalGuardByRegistrationUseCase) {
    this.acautelamentoRepository = acautelamentoRepository;
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
    Long idAcautelamento = equipamentReturnDTO.idAcautelamento();
    Optional<Acautelamento> acautelamentoOptional = acautelamentoRepository.findById(idAcautelamento);
    if (!acautelamentoOptional.isPresent()) {
      throw new AcautelamentoNaoEncontradoException(idAcautelamento);
    }

    // increase stock item quantity
    Acautelamento acautelamento = acautelamentoOptional.get();
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
      itemDevolvido.setAcautelamento(acautelamento);

      itensDevolvidos.add(itemDevolvido);
    }

    Devolucao devolucao = new Devolucao(acautelamento, LocalDateTime.now(), gmOptional.get(), armoryKeeper.get());
    itensDevolvidos.forEach(devolucao::addItemDevolvido);
    returnEquipamentRepository.save(devolucao);
  }
}
