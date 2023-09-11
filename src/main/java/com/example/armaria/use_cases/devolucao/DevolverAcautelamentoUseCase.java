package com.example.armaria.use_cases.devolucao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.armaria.dtos.devolucao.DevolverAcautelamentoDTO;
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
  private final DevolucaoEquipamentoRepository devolverEquipamentoRepository;
  private final ItemEstoqueRepository itemEstoqueRepository;
  private final GetArmoryKeeperByRegistrationUseCase getArmoryKeeperByRegistrationUseCase;
  private final GetMunicipalGuardByRegistrationUseCase getMunicipalGuardByRegistrationUseCase;

  public DevolverAcautelamentoUseCase(
      AcautelamentoRepository acautelamentoRepository,
      DevolucaoEquipamentoRepository devolverEquipamentoRepository,
      ItemEstoqueRepository itemEstoqueRepository,
      GetArmoryKeeperByRegistrationUseCase getArmoryKeeperByRegistrationUseCase,
      GetMunicipalGuardByRegistrationUseCase getMunicipalGuardByRegistrationUseCase) {
    this.acautelamentoRepository = acautelamentoRepository;
    this.itemEstoqueRepository = itemEstoqueRepository;
    this.devolverEquipamentoRepository = devolverEquipamentoRepository;
    this.getArmoryKeeperByRegistrationUseCase = getArmoryKeeperByRegistrationUseCase;
    this.getMunicipalGuardByRegistrationUseCase = getMunicipalGuardByRegistrationUseCase;
  }

  @Transactional
  public void execute(DevolverAcautelamentoDTO devolverEquipamentosDto) {
    // verificar se gm existe
    String armoryKeeperRegistrationNumber = devolverEquipamentosDto.armoryKeeperRegistration();
    Optional<ArmoryKepper> armoryKeeper = getArmoryKeeperByRegistrationUseCase
        .execute(armoryKeeperRegistrationNumber);

    // check if municipal guard exists
    String registration = devolverEquipamentosDto.municipalGuardRegistration();
    Optional<MunicipalGuard> gmOptional = getMunicipalGuardByRegistrationUseCase.execute(registration);

    // verificar se acautelamento existe
    Long idAcautelamento = devolverEquipamentosDto.idAcautelamento();
    Optional<Acautelamento> acautelamentoOptional = acautelamentoRepository.findById(idAcautelamento);
    if (!acautelamentoOptional.isPresent()) {
      throw new AcautelamentoNaoEncontradoException(idAcautelamento);
    }

    // aumentar o estoque para cada item devolvido
    Acautelamento acautelamento = acautelamentoOptional.get();
    List<ItemDevolvido> itensDevolvidos = new ArrayList<>();
    for (ItemDevolvidoDTO item : devolverEquipamentosDto.itensDevolvidos()) {
      int linhasAfetadas = itemEstoqueRepository.aumentarQuantidadeEmEstoque(item.idItemEstoque(),
          item.quantidadeDevolvida());

      if (linhasAfetadas == 0) {
        throw new QuantidadeEmEstoqueNaoAumentadaException(item.idItemEstoque());
      }

      // List<ItemAcautelado> itensAcautelados = acautelamento.getItensAcautelados();
      // Optional<ItemAcautelado> itemAcauteladoOptional = itensAcautelados.stream()
      // .filter(itemAcautelado ->
      // itemAcautelado.getItemEstoque().getId().equals(item.idItemEstoque()))
      // .findFirst();

      // // int diferencaQuantidadeAcauteladaEDevolvida =
      // itemAcauteladoOptional.get().getQuantidadeAcautelada()
      // // - item.quantidadeDevolvida();

      // if (!itemAcauteladoOptional.isPresent() ||
      // diferencaQuantidadeAcauteladaEDevolvida > 0) {
      // // TODO: registrar perda/dano
      // }

      // salvar devolucao
      ItemEstoque itemEstoque = new ItemEstoque();
      itemEstoque.setId(item.idItemEstoque());

      ItemDevolvido itemDevolvido = new ItemDevolvido();
      itemDevolvido.setItemEstoque(itemEstoque);
      itemDevolvido.setQuantidadeDevolvida(item.quantidadeDevolvida());
      itemDevolvido.setAcautelamento(acautelamento);

      itensDevolvidos.add(itemDevolvido);
    }

    Devolucao devolucao = new Devolucao(acautelamento, LocalDateTime.now(), gmOptional.get(), armoryKeeper.get());
    itensDevolvidos.forEach(devolucao::adicionarEquipamento);
    devolverEquipamentoRepository.save(devolucao);
  }
}
