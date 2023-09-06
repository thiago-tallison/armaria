package com.example.armaria.use_cases.acautelamento;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.armaria.dtos.acautelamento.CriarAcautelamentoDTO;
import com.example.armaria.entities.Acautelamento;
import com.example.armaria.entities.Armeiro;
import com.example.armaria.entities.Equipamento;
import com.example.armaria.entities.GuardaMunicipal;
import com.example.armaria.entities.ItemAcautelado;
import com.example.armaria.errors.ArmeiroNaoEncontradoException;
import com.example.armaria.errors.GuardaMunicipalNaoEncontradoException;
import com.example.armaria.repositories.AcautelamentoRepository;
import com.example.armaria.repositories.ArmeiroRepository;
import com.example.armaria.repositories.GuardaMunicipalRepository;
import com.example.armaria.repositories.ItemEstoqueRepository;

import jakarta.transaction.Transactional;

@Service
public class CriarAcautelamentoUseCase {
  private final AcautelamentoRepository acautelamentoRepository;
  private final ItemEstoqueRepository itemEstoqueRepository;
  private final ArmeiroRepository armeiroRepository;
  private final GuardaMunicipalRepository gmRepository;

  public CriarAcautelamentoUseCase(
      AcautelamentoRepository acautelamentoRepository,
      ItemEstoqueRepository itemEstoqueRepository,
      ArmeiroRepository armeiroRepository,
      GuardaMunicipalRepository gmRepository) {
    this.acautelamentoRepository = acautelamentoRepository;
    this.itemEstoqueRepository = itemEstoqueRepository;
    this.armeiroRepository = armeiroRepository;
    this.gmRepository = gmRepository;
  }

  @Transactional
  public void execute(CriarAcautelamentoDTO data) {
    // verificar se o armeiro existe
    String matriculaArmeiro = data.matriculaArmeiro();
    Optional<Armeiro> armeiroOptional = armeiroRepository.findByMatricula(matriculaArmeiro);
    if (!armeiroOptional.isPresent()) {
      throw new ArmeiroNaoEncontradoException(matriculaArmeiro);
    }

    // verificar se o gm existe
    String matriculaGm = data.matriculaGm();
    Optional<GuardaMunicipal> gmOptional = gmRepository
        .findByMatricula(matriculaGm);

    if (!gmOptional.isPresent()) {
      throw new GuardaMunicipalNaoEncontradoException(matriculaGm);
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
        armeiroOptional.get());
    itensAcautelados.forEach(acautelamento::adicionarEquipamento);

    acautelamentoRepository.save(acautelamento);
  }
}
