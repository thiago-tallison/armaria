package com.example.armaria.use_cases.acautelamento;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.armaria.entities.Acautelamento;
import com.example.armaria.entities.Armeiro;
import com.example.armaria.entities.GuardaMunicipal;
import com.example.armaria.entities.ItemAcautelado;
import com.example.armaria.entities.ItemEstoque;
import com.example.armaria.errors.ArmeiroNaoEncontradoException;
import com.example.armaria.errors.GuardaMunicipalNaoEncontradoException;
import com.example.armaria.errors.ItemEstoqueNaoEncontradoException;
import com.example.armaria.repositories.ArmeiroRepository;
import com.example.armaria.repositories.GuardaMunicipalRepository;
import com.example.armaria.repositories.ItemEstoqueRepository;

import jakarta.transaction.Transactional;

record ItemAcauteladoRequest(
    Long id,
    String id_equipamento,
    Integer quantidadeAcautelada) {
}

record CriarAcautelamentoRequest(
    LocalDateTime dataAcautelamento,
    String matriculaGm,
    String matriculaArmeiro,
    List<ItemAcauteladoRequest> itensAcautelados) {
}

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
  public void execute(CriarAcautelamentoRequest data) {
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
    List<ItemAcauteladoRequest> itens = data.itensAcautelados();
    List<ItemAcautelado> itensAcautelados = new ArrayList<>();

    for (int i = 0; i < itens.size(); i++) {
      Long idAtual = itens.get(i).id();
      Integer quantidadeASerAcautelada = itens.get(i).quantidadeAcautelada();

      Optional<ItemEstoque> itemEstoqueOptional = itemEstoqueRepository
          .existeEQuantidadeDisponivelEmEstoque(idAtual, quantidadeASerAcautelada);

      if (!itemEstoqueOptional.isPresent()) {
        throw new ItemEstoqueNaoEncontradoException(String.valueOf(itens.get(i).id()));
      }

      itemEstoqueRepository.diminuirQuantidadeEmEstoque(idAtual, quantidadeASerAcautelada);
    }

    Acautelamento acautelamento = new Acautelamento(null, LocalDateTime.now(), gmOptional.get(), armeiroOptional.get());
    itensAcautelados.forEach(item -> acautelamento.adicionarEquipamento(item));

    acautelamentoRepository.save(acautelamento);
  }
}
