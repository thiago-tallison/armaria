package com.example.armaria.use_cases.equipamento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.armaria.entities.Equipamento;
import com.example.armaria.errors.PaginaNaoExistenteException;
import com.example.armaria.errors.TamanhoDaPaginaNegativoException;
import com.example.armaria.errors.TamanhoDePaginaExcedeuOLimiteException;
import com.example.armaria.repositories.EquipamentoRepository;

import jakarta.transaction.Transactional;

@Service
public class ListarEquipamentosUseCase {
  private final EquipamentoRepository equipamentoRepository;
  public static final int TAMANHO_MAXIMO_PAGINA = 30;

  @Autowired
  public ListarEquipamentosUseCase(EquipamentoRepository equipamentoRepository) {
    this.equipamentoRepository = equipamentoRepository;
  }

  @Transactional
  public ListarEquipamentoResponseDTO execute(ListarEquipamentosRequestDTO listarEquipamentosRequestDTO) {
    int pagina = listarEquipamentosRequestDTO.pagina();
    int tamanhoPagina = listarEquipamentosRequestDTO.itensPorPagina();

    if (tamanhoPagina <= 0) {
      throw new TamanhoDaPaginaNegativoException(tamanhoPagina);
    }

    if (tamanhoPagina > TAMANHO_MAXIMO_PAGINA) {
      throw new TamanhoDePaginaExcedeuOLimiteException();
    }

    Pageable pageable = PageRequest.of(pagina, tamanhoPagina);

    Page<Equipamento> equipamentos = equipamentoRepository.findAll(pageable);

    if (pagina >= equipamentos.getTotalPages() || pagina < 0) {
      throw new PaginaNaoExistenteException();
    }

    ListarEquipamentoResponseDTO dto = new ListarEquipamentoResponseDTO(pagina, "",
        "", equipamentos.getTotalElements(), equipamentos.getTotalPages(),
        equipamentos.toList());

    return dto;
  }
}
