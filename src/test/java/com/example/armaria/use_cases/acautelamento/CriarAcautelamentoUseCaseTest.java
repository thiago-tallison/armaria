package com.example.armaria.use_cases.acautelamento;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Description;

import com.example.armaria.dtos.acautelamento.CriarAcautelamentoDTO;
import com.example.armaria.entities.Acautelamento;
import com.example.armaria.entities.Armeiro;
import com.example.armaria.entities.Equipamento;
import com.example.armaria.entities.GuardaMunicipal;
import com.example.armaria.entities.ItemEstoque;
import com.example.armaria.errors.ArmeiroNaoEncontradoException;
import com.example.armaria.errors.GuardaMunicipalNaoEncontradoException;
import com.example.armaria.repositories.AcautelamentoRepository;
import com.example.armaria.repositories.ArmeiroRepository;
import com.example.armaria.repositories.GuardaMunicipalRepository;
import com.example.armaria.repositories.ItemEstoqueRepository;

public class CriarAcautelamentoUseCaseTest {
  @InjectMocks
  private CriarAcautelamentoUseCase sut;

  @Mock
  private AcautelamentoRepository acautelamentoRepository;

  @Mock
  private ItemEstoqueRepository itemEstoqueRepository;

  @Mock
  private ArmeiroRepository armeiroRepository;

  @Mock
  private GuardaMunicipalRepository gmRepository;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testCriarAcautelamentoSucesso() {
    // Configurar objetos mock
    LocalDateTime dataAcautelamento = LocalDateTime.now();
    String matriculaGm = "GM123";
    String matriculaArmeiro = "AR123";
    List<ItemAcauteladoDTO> itensAcauteladosRequest = new ArrayList<>();
    itensAcauteladosRequest.add(new ItemAcauteladoDTO(1L, 1L, 2));

    Armeiro armeiroMock = new Armeiro();
    armeiroMock.setMatricula(matriculaArmeiro);

    GuardaMunicipal gmMock = new GuardaMunicipal();
    gmMock.setMatricula(matriculaGm);

    ItemEstoque itemEstoqueMock = new ItemEstoque();
    Equipamento equipamentoMock = new Equipamento();
    equipamentoMock.setId(1L);
    itemEstoqueMock.setEquipamento(equipamentoMock);
    itemEstoqueMock.setQuantidadeEmEstoque(5);

    when(armeiroRepository.findByMatricula(matriculaArmeiro)).thenReturn(Optional.of(armeiroMock));
    when(gmRepository.findByMatricula(matriculaGm)).thenReturn(Optional.of(gmMock));
    when(itemEstoqueRepository.diminuirQuantidadeEmEstoque(eq(1L), eq(2))).thenReturn(1);

    // Executar o caso de uso
    CriarAcautelamentoDTO request = new CriarAcautelamentoDTO(dataAcautelamento, matriculaGm, matriculaArmeiro,
        itensAcauteladosRequest);
    assertDoesNotThrow(() -> sut.execute(request));

    // Verificar se o acautelamento foi salvo
    ArgumentCaptor<Acautelamento> acautelamentoCaptor = ArgumentCaptor.forClass(Acautelamento.class);
    verify(acautelamentoRepository).save(acautelamentoCaptor.capture());
    Acautelamento acautelamentoSalvo = acautelamentoCaptor.getValue();

    assertNotNull(acautelamentoSalvo);
    assertEquals(dataAcautelamento, acautelamentoSalvo.getDataAcautelamento());
    assertEquals(gmMock, acautelamentoSalvo.getGm());
    assertEquals(armeiroMock, acautelamentoSalvo.getArmeiro());
    assertEquals(1, acautelamentoSalvo.getTotalEquipamentosAcautelados());
  }

  @Test
  void testCriarAcautelamentoArmeiroNaoEncontrado() {
    // Configurar objetos mock
    LocalDateTime dataAcautelamento = LocalDateTime.now();
    String matriculaGm = "GM123";
    String matriculaArmeiro = "AR123";
    List<ItemAcauteladoDTO> itensAcauteladosRequest = new ArrayList<>();
    itensAcauteladosRequest.add(new ItemAcauteladoDTO(1L, 1L, 2));

    Armeiro armeiroMock = new Armeiro();
    armeiroMock.setMatricula(matriculaArmeiro);

    GuardaMunicipal gmMock = new GuardaMunicipal();
    gmMock.setMatricula(matriculaGm);

    ItemEstoque itemEstoqueMock = new ItemEstoque();
    Equipamento equipamentoMock = new Equipamento();
    equipamentoMock.setId(1L);
    itemEstoqueMock.setEquipamento(equipamentoMock);
    itemEstoqueMock.setQuantidadeEmEstoque(5);

    when(armeiroRepository.findByMatricula(matriculaArmeiro)).thenReturn(Optional.empty());

    // Executar o caso de uso
    CriarAcautelamentoDTO request = new CriarAcautelamentoDTO(dataAcautelamento, matriculaGm, matriculaArmeiro,
        itensAcauteladosRequest);
    assertThrows(ArmeiroNaoEncontradoException.class, () -> sut.execute(request));

    verify(acautelamentoRepository, never()).save(any());
  }

  @Test
  void testCriarAcautelamentoGmNaoEncontrado() {
    // Configurar objetos mock
    LocalDateTime dataAcautelamento = LocalDateTime.now();
    String matriculaGm = "GM123";
    String matriculaArmeiro = "AR123";
    List<ItemAcauteladoDTO> itensAcauteladosRequest = new ArrayList<>();
    itensAcauteladosRequest.add(new ItemAcauteladoDTO(1L, 1L, 2));

    when(armeiroRepository.findByMatricula(matriculaArmeiro)).thenReturn(Optional.of(new Armeiro()));
    when(gmRepository.findByMatricula(matriculaGm)).thenReturn(Optional.empty());

    // Executar o caso de uso
    CriarAcautelamentoDTO request = new CriarAcautelamentoDTO(dataAcautelamento, matriculaGm, matriculaArmeiro,
        itensAcauteladosRequest);

    // Verificar se GuardaMunicipalNaoEncontradoException é lançada
    assertThrows(GuardaMunicipalNaoEncontradoException.class, () -> sut.execute(request));

    // Verificar que acautelamentoRepository.save() nunca foi chamado
    verify(acautelamentoRepository, never()).save(any());
  }

  @Test
  @Description("Não deve ser possível criar um acautelamento se a quantidade de itens acautelados for maior que a quantidade em estoque")
  void testCriarAcautelamentoQuantidadeInsuficienteNoEstoque() {
    LocalDateTime dataAcautelamento = LocalDateTime.now();
    String matriculaGm = "GM123";
    String matriculaArmeiro = "AR123";
    List<ItemAcauteladoDTO> itensAcauteladosRequest = new ArrayList<>();
    itensAcauteladosRequest.add(new ItemAcauteladoDTO(1L, 1L, 10)); // Quantidade alta para forçar erro

    Armeiro armeiroMock = new Armeiro();
    armeiroMock.setMatricula(matriculaArmeiro);

    GuardaMunicipal gmMock = new GuardaMunicipal();
    gmMock.setMatricula(matriculaGm);

    ItemEstoque itemEstoqueMock = new ItemEstoque();
    Equipamento equipamentoMock = new Equipamento();
    equipamentoMock.setId(1L);
    itemEstoqueMock.setEquipamento(equipamentoMock);
    itemEstoqueMock.setQuantidadeEmEstoque(5); // Quantidade em estoque insuficiente

    when(armeiroRepository.findByMatricula(matriculaArmeiro)).thenReturn(Optional.of(armeiroMock));
    when(gmRepository.findByMatricula(matriculaGm)).thenReturn(Optional.of(gmMock));
    when(itemEstoqueRepository.diminuirQuantidadeEmEstoque(eq(1L), eq(10))).thenReturn(0); // Retornar 0 para simular
                                                                                           // erro

    // Executar o caso de uso
    CriarAcautelamentoDTO request = new CriarAcautelamentoDTO(dataAcautelamento, matriculaGm, matriculaArmeiro,
        itensAcauteladosRequest);

    // Verificar se RuntimeException é lançada devido à quantidade insuficiente no
    // estoque
    assertThrows(RuntimeException.class, () -> sut.execute(request));

    // Verificar que acautelamentoRepository.save() nunca foi chamado
    verify(acautelamentoRepository, never()).save(any());
  }

}
