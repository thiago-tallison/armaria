package com.example.armaria.use_cases.equipamento;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.example.armaria.entities.Equipamento;
import com.example.armaria.errors.TamanhoDaPaginaNegativoException;
import com.example.armaria.errors.TamanhoDePaginaExcedeuOLimiteException;
import com.example.armaria.repositories.EquipamentoRepository;

public class ListarEquipamentosUseCaseTest {
  private ListarEquipamentosUseCase listarEquipamentosUseCase;

  @Mock
  private EquipamentoRepository equpamentoRepository;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    listarEquipamentosUseCase = new ListarEquipamentosUseCase(equpamentoRepository);
  }

  @Test
  void testExecute() {
    int pagina = 0;
    int intesPorPagina = 3;

    List<Equipamento> equipamentos = getEquipamentosList();

    Pageable pageable = PageRequest.of(pagina, intesPorPagina);

    when(equpamentoRepository.findAll(pageable))
        .thenReturn(new PageImpl<>(equipamentos.subList(0, intesPorPagina), pageable, equipamentos.size()));

    ListarEquipamentosRequestDTO listarDto = new ListarEquipamentosRequestDTO(intesPorPagina, pagina);

    ListarEquipamentoResponseDTO response = listarEquipamentosUseCase.execute(listarDto);

    assertNotNull(response);
    assertTrue(response.itens().size() <= intesPorPagina);
  }

  @Test
  void testExecute_tamanho_pagina_negativo_deve_lancar_erro() {
    int pagina = 0;
    int intesPorPagina = -1;

    ListarEquipamentosRequestDTO listarDto = new ListarEquipamentosRequestDTO(intesPorPagina, pagina);

    assertThrows(TamanhoDaPaginaNegativoException.class, () -> listarEquipamentosUseCase.execute(listarDto));
  }

  @Test
  void testExecute_tamanho_pagina_maior_que_o_limite_deve_lancar_erro() {
    int pagina = 0;
    int tamanhoMaiorQueOLimite = ListarEquipamentosUseCase.TAMANHO_MAXIMO_PAGINA + 1;

    ListarEquipamentosRequestDTO listarDto = new ListarEquipamentosRequestDTO(tamanhoMaiorQueOLimite, pagina);

    assertThrows(TamanhoDePaginaExcedeuOLimiteException.class, () -> listarEquipamentosUseCase.execute(listarDto));
  }

  private List<Equipamento> getEquipamentosList() {
    Equipamento e1 = new Equipamento("Equipamento 1", "num-serie-1", true);
    Equipamento e2 = new Equipamento("Equipamento 2", "num-serie-1", true);
    Equipamento e3 = new Equipamento("Equipamento 3", "num-serie-1", true);
    Equipamento e4 = new Equipamento("Equipamento 4", "num-serie-1", true);
    Equipamento e5 = new Equipamento("Equipamento 5", "num-serie-1", true);

    List<Equipamento> equipamentos = List.of(e1, e2, e3, e4, e5);

    return equipamentos;
  }
}
