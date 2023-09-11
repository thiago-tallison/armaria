package com.example.armaria.use_cases.acautelamento;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.armaria.dtos.acautelamento.CriarAcautelamentoDTO;
import com.example.armaria.entities.Acautelamento;
import com.example.armaria.entities.ArmoryKepper;
import com.example.armaria.entities.Equipamento;
import com.example.armaria.entities.ItemAcautelado;
import com.example.armaria.entities.MunicipalGuard;
import com.example.armaria.errors.ArmoryKeeperNotFoundException;
import com.example.armaria.errors.MunicipalGuardNotFoundException;
import com.example.armaria.repositories.AcautelamentoRepository;
import com.example.armaria.repositories.ArmoryKeeperRepository;
import com.example.armaria.repositories.ItemEstoqueRepository;
import com.example.armaria.repositories.MunicipalGuardRepository;

import jakarta.transaction.Transactional;

@Service
public class CriarAcautelamentoUseCase {
  private final AcautelamentoRepository acautelamentoRepository;
  private final ItemEstoqueRepository itemEstoqueRepository;
  private final ArmoryKeeperRepository armoryKeeperRepository;
  private final MunicipalGuardRepository gmRepository;

  public CriarAcautelamentoUseCase(
      AcautelamentoRepository acautelamentoRepository,
      ItemEstoqueRepository itemEstoqueRepository,
      ArmoryKeeperRepository armoryKeeperRepository,
      MunicipalGuardRepository gmRepository) {
    this.acautelamentoRepository = acautelamentoRepository;
    this.itemEstoqueRepository = itemEstoqueRepository;
    this.armoryKeeperRepository = armoryKeeperRepository;
    this.gmRepository = gmRepository;
  }

  @Transactional
  public void execute(CriarAcautelamentoDTO data) {
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
    List<ItemAcauteladoDTO> itens = data.itensAcautelados();
    List<ItemAcautelado> itensAcautelados = new ArrayList<>();

    for (int i = 0; i < itens.size(); i++) {
      Long idAtual = itens.get(i).id();
      Integer quantidadeASerAcautelada = itens.get(i).quantidadeAcautelada();

      int linhasAfetadas = itemEstoqueRepository
          .diminuirQuantidadeEmEstoque(idAtual, quantidadeASerAcautelada);

      if (linhasAfetadas == 0) {
        throw new RuntimeException("Não foi possível acautelar o item " + idAtual);
      }

      Equipamento equipamento = new Equipamento();
      equipamento.setId(itens.get(i).idEquipamento());

      itensAcautelados.add(new ItemAcautelado(equipamento,
          quantidadeASerAcautelada));
    }

    Acautelamento acautelamento = new Acautelamento(null, data.dataAcautelamento(), gmOptional.get(),
        optionalArmoryKeeper.get());
    itensAcautelados.forEach(acautelamento::adicionarEquipamento);

    acautelamentoRepository.save(acautelamento);
  }
}
