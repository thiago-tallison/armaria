package com.example.armaria.use_cases.equipament;

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

import com.example.armaria.core.domain.Equipament;
import com.example.armaria.errors.PageSizeOutOfBoundException;
import com.example.armaria.errors.TamanhoDaPaginaNegativoException;
import com.example.armaria.repositories.EquipamentRepository;

public class ListarEquipamentsUseCaseTest {
  private ListEquipamentsUseCase listEquipamentsUseCase;

  @Mock
  private EquipamentRepository equpamentoRepository;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    listEquipamentsUseCase = new ListEquipamentsUseCase(equpamentoRepository);
  }

  @Test
  void testExecute() {
    int pagina = 0;
    int intesPorPagina = 3;

    List<Equipament> equipaments = getEquipaments();

    Pageable pageable = PageRequest.of(pagina, intesPorPagina);

    when(equpamentoRepository.findAll(pageable))
        .thenReturn(new PageImpl<>(equipaments.subList(0, intesPorPagina), pageable, equipaments.size()));

    EquipamentsListDTO listarDto = new EquipamentsListDTO(intesPorPagina, pagina);

    EquipamentsListViewModel response = listEquipamentsUseCase.execute(listarDto);

    assertNotNull(response);
    assertTrue(response.itens().size() <= intesPorPagina);
  }

  @Test
  void testExecute_tamanho_pagina_negativo_deve_lancar_erro() {
    int pagina = 0;
    int intesPorPagina = -1;

    EquipamentsListDTO listarDto = new EquipamentsListDTO(intesPorPagina, pagina);

    assertThrows(TamanhoDaPaginaNegativoException.class, () -> listEquipamentsUseCase.execute(listarDto));
  }

  @Test
  void testExecute_tamanho_pagina_maior_que_o_limite_deve_lancar_erro() {
    int pagina = 0;
    int tamanhoMaiorQueOLimite = ListEquipamentsUseCase.TAMANHO_MAXIMO_PAGINA + 1;

    EquipamentsListDTO listarDto = new EquipamentsListDTO(tamanhoMaiorQueOLimite, pagina);

    assertThrows(PageSizeOutOfBoundException.class, () -> listEquipamentsUseCase.execute(listarDto));
  }

  private List<Equipament> getEquipaments() {
    Equipament e1 = new Equipament("Equipament 1", "serialNumber-1", true);
    Equipament e2 = new Equipament("Equipament 2", "serialNumber-2", true);
    Equipament e3 = new Equipament("Equipament 3", "serialNumber-3", true);
    Equipament e4 = new Equipament("Equipament 4", "serialNumber-4", true);
    Equipament e5 = new Equipament("Equipament 5", "serialNumber-5", true);

    List<Equipament> equipaments = List.of(e1, e2, e3, e4, e5);

    return equipaments;
  }
}
