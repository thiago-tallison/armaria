package com.example.armaria.use_cases.devolucao;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.armaria.dtos.devolucao.DevolverAcautelamentoDTO;
import com.example.armaria.dtos.devolucao.ItemDevolvidoDTO;
import com.example.armaria.entities.Acautelamento;
import com.example.armaria.entities.Armeiro;
import com.example.armaria.entities.Devolucao;
import com.example.armaria.entities.GuardaMunicipal;
import com.example.armaria.entities.ItemEstoque;
import com.example.armaria.errors.AcautelamentoNaoEncontradoException;
import com.example.armaria.errors.ArmeiroNaoEncontradoException;
import com.example.armaria.errors.GuardaMunicipalNaoEncontradoException;
import com.example.armaria.repositories.AcautelamentoRepository;
import com.example.armaria.repositories.ItemEstoqueRepository;
import com.example.armaria.use_cases.armeiro.BuscarArmeiroPorMatriculaUseCase;
import com.example.armaria.use_cases.guarda_municipal.BuscarGuardaMunicipalPorMatriculaUseCase;

public class DevolverAcautelamentoUseCaseTest {
  @InjectMocks
  DevolverAcautelamentoUseCase sut;

  @Mock
  private AcautelamentoRepository acautelamentoRepository;

  @Mock
  private DevolucaoEquipamentoRepository devolverEquipamentoRepository;

  @Mock
  private ItemEstoqueRepository itemEstoqueRepository;

  @Mock
  private BuscarArmeiroPorMatriculaUseCase buscarArmeiroPorMatriculaUseCase;

  @Mock
  private BuscarGuardaMunicipalPorMatriculaUseCase buscarGuardaMunicipalPorMatriculaUseCase;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void deveCriarDevolucao() {
    DevolverAcautelamentoDTO devolverEquipamentosDto = createValidDTO();

    Armeiro armeiroMock = new Armeiro();
    armeiroMock.setMatricula(devolverEquipamentosDto.matriculaArmeiro());

    GuardaMunicipal gmMock = new GuardaMunicipal();
    gmMock.setMatricula(devolverEquipamentosDto.matriculaGm());

    Acautelamento acatuelamentoMock = new Acautelamento();
    acatuelamentoMock.setId(devolverEquipamentosDto.idAcautelamento());

    ItemEstoque itemEstoqueMock = new ItemEstoque();
    itemEstoqueMock.setId(devolverEquipamentosDto.itensDevolvidos().get(0).idItemEstoque());

    Devolucao devolucaoMock = new Devolucao(acatuelamentoMock, devolverEquipamentosDto.dataDevolucao(), gmMock,
        armeiroMock);

    when(buscarArmeiroPorMatriculaUseCase.execute(armeiroMock.getMatricula())).thenReturn(Optional.of(armeiroMock));
    when(buscarGuardaMunicipalPorMatriculaUseCase.execute(gmMock.getMatricula())).thenReturn(Optional.of(gmMock));
    when(acautelamentoRepository.findById(acatuelamentoMock.getId())).thenReturn(Optional.of(acatuelamentoMock));
    when(itemEstoqueRepository.aumentarQuantidadeEmEstoque(itemEstoqueMock.getId(),
        devolverEquipamentosDto.itensDevolvidos().get(0).quantidadeDevolvida())).thenReturn(1);
    when(devolverEquipamentoRepository.save(devolucaoMock)).thenReturn(devolucaoMock);

    assertDoesNotThrow(() -> sut.execute(devolverEquipamentosDto));

    verify(itemEstoqueRepository, times(1)).aumentarQuantidadeEmEstoque(
        itemEstoqueMock.getId(),
        devolverEquipamentosDto.itensDevolvidos().get(0).quantidadeDevolvida());

    verify(devolverEquipamentoRepository, times(1)).save(any(Devolucao.class));
  }

  @Test
  public void naoDeveCriarDevolucaoArmeiroMatriculaInvalida() {
    DevolverAcautelamentoDTO dtoMatriculaInvalida = createValidDTO()
        .withMatriculaArmeiro("matricula-que-nao-existe");

    Acautelamento acautelamento = createAcautelamento();

    when(buscarArmeiroPorMatriculaUseCase.execute(eq("matricula-que-nao-existe")))
        .thenThrow(ArmeiroNaoEncontradoException.class);

    when(acautelamentoRepository.findById(anyLong())).thenReturn(Optional.of(acautelamento));

    assertThrows(ArmeiroNaoEncontradoException.class, () -> sut.execute(dtoMatriculaInvalida));

    verify(buscarArmeiroPorMatriculaUseCase).execute(eq("matricula-que-nao-existe"));
    verify(itemEstoqueRepository, never()).aumentarQuantidadeEmEstoque(anyLong(), anyInt());
    verify(acautelamentoRepository, never()).findById(anyLong());
  }

  @Test
  public void naoDeveCriarDevolucaoGMMatriculaInvalida() {
    DevolverAcautelamentoDTO dtoMatriculaInvalida = createValidDTO()
        .withMatriculaGM("matricula-que-nao-existe");

    Acautelamento acautelamento = createAcautelamento();

    when(buscarGuardaMunicipalPorMatriculaUseCase.execute(eq("matricula-que-nao-existe")))
        .thenThrow(GuardaMunicipalNaoEncontradoException.class);
    when(acautelamentoRepository.findById(anyLong())).thenReturn(Optional.of(acautelamento));

    assertThrows(GuardaMunicipalNaoEncontradoException.class, () -> sut.execute(dtoMatriculaInvalida));

    verify(buscarGuardaMunicipalPorMatriculaUseCase).execute(eq("matricula-que-nao-existe"));
    verify(itemEstoqueRepository, never()).aumentarQuantidadeEmEstoque(anyLong(), anyInt());
    verify(acautelamentoRepository, never()).findById(anyLong());
  }

  @Test
  public void naoDeveCriarDevolucaoAcautelamentoNaoExistente() {
    DevolverAcautelamentoDTO devolverEquipamentosDto = createValidDTO();

    Armeiro armeiroMock = new Armeiro();
    armeiroMock.setMatricula(devolverEquipamentosDto.matriculaArmeiro());

    GuardaMunicipal gmMock = new GuardaMunicipal();
    gmMock.setMatricula(devolverEquipamentosDto.matriculaGm());

    Acautelamento acatuelamentoMock = new Acautelamento();
    long idNaoExistente = 1001L;
    acatuelamentoMock.setId(idNaoExistente);

    ItemEstoque itemEstoqueMock = new ItemEstoque();
    itemEstoqueMock.setId(devolverEquipamentosDto.itensDevolvidos().get(0).idItemEstoque());

    when(buscarArmeiroPorMatriculaUseCase.execute(armeiroMock.getMatricula())).thenReturn(Optional.of(armeiroMock));
    when(buscarGuardaMunicipalPorMatriculaUseCase.execute(gmMock.getMatricula())).thenReturn(Optional.of(gmMock));
    when(acautelamentoRepository.findById(idNaoExistente)).thenThrow(AcautelamentoNaoEncontradoException.class);

    assertThrows(AcautelamentoNaoEncontradoException.class, () -> sut.execute(devolverEquipamentosDto));

    verify(itemEstoqueRepository, never()).aumentarQuantidadeEmEstoque(
        anyLong(),
        anyInt());

    verify(devolverEquipamentoRepository, never()).save(any(Devolucao.class));
  }

  private Acautelamento createAcautelamento() {
    return new Acautelamento();
  }

  private DevolverAcautelamentoDTO createValidDTO() {
    // given
    LocalDateTime dataAcautelamento = LocalDateTime.now();
    Long idAcautelamento = 1L;
    String matriculaGm = "matricula-gm";
    String matriculaArmeiro = "matricula-armeiro";
    List<ItemDevolvidoDTO> itensDevolvidos = new ArrayList<>();
    ItemDevolvidoDTO itemDto = new ItemDevolvidoDTO(1L, 2);
    itensDevolvidos.add(itemDto);

    DevolverAcautelamentoDTO dto = new DevolverAcautelamentoDTO(
        dataAcautelamento,
        matriculaGm,
        idAcautelamento,
        matriculaArmeiro,
        itensDevolvidos);

    return dto;
  }
}
