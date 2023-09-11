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
import com.example.armaria.entities.ArmoryKepper;
import com.example.armaria.entities.Equipament;
import com.example.armaria.entities.ItemEstoque;
import com.example.armaria.entities.MunicipalGuard;
import com.example.armaria.errors.ArmoryKeeperNotFoundException;
import com.example.armaria.errors.MunicipalGuardNotFoundException;
import com.example.armaria.repositories.AcautelamentoRepository;
import com.example.armaria.repositories.ArmoryKeeperRepository;
import com.example.armaria.repositories.ItemEstoqueRepository;
import com.example.armaria.repositories.MunicipalGuardRepository;

public class CriarAcautelamentoUseCaseTest {
  @InjectMocks
  private CriarAcautelamentoUseCase sut;

  @Mock
  private AcautelamentoRepository acautelamentoRepository;

  @Mock
  private ItemEstoqueRepository itemEstoqueRepository;

  @Mock
  private ArmoryKeeperRepository armoryKeeperRepository;

  @Mock
  private MunicipalGuardRepository gmRepository;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testCriarAcautelamentoSucesso() {
    // Configurar objetos mock
    LocalDateTime dataAcautelamento = LocalDateTime.now();
    String registration = "municipal-guard-registration";
    String armoryKeeperRegistration = "armory-keeper-registration";
    List<ItemAcauteladoDTO> itensAcauteladosRequest = new ArrayList<>();
    itensAcauteladosRequest.add(new ItemAcauteladoDTO(1L, 1L, 2));

    ArmoryKepper armoryKeeperMock = new ArmoryKepper();
    armoryKeeperMock.setRegistrationNumber(armoryKeeperRegistration);

    MunicipalGuard gmMock = new MunicipalGuard();
    gmMock.setRegistrationNumber(registration);

    ItemEstoque itemEstoqueMock = new ItemEstoque();
    Equipament equipamentMock = new Equipament();
    equipamentMock.setId(1L);
    itemEstoqueMock.setEquipament(equipamentMock);
    itemEstoqueMock.setQuantityInStock(5);

    when(armoryKeeperRepository.findByRegistrationNumber(armoryKeeperRegistration))
        .thenReturn(Optional.of(armoryKeeperMock));
    when(gmRepository.findByRegistrationNumber(registration)).thenReturn(Optional.of(gmMock));
    when(itemEstoqueRepository.diminuirQuantidadeEmEstoque(eq(1L), eq(2))).thenReturn(1);

    // Executar o caso de uso
    CriarAcautelamentoDTO request = new CriarAcautelamentoDTO(dataAcautelamento, registration, armoryKeeperRegistration,
        itensAcauteladosRequest);
    assertDoesNotThrow(() -> sut.execute(request));

    // Verificar se o acautelamento foi salvo
    ArgumentCaptor<Acautelamento> acautelamentoCaptor = ArgumentCaptor.forClass(Acautelamento.class);
    verify(acautelamentoRepository).save(acautelamentoCaptor.capture());
    Acautelamento acautelamentoSalvo = acautelamentoCaptor.getValue();

    assertNotNull(acautelamentoSalvo);
    assertEquals(dataAcautelamento, acautelamentoSalvo.getDataAcautelamento());
    assertEquals(gmMock, acautelamentoSalvo.getGuard());
    assertEquals(armoryKeeperMock, acautelamentoSalvo.getArmoryKeeper());
    assertEquals(1, acautelamentoSalvo.getTotalEquipamentsAcautelados());
  }

  @Test
  void testCriarAcautelamentoArmeiroNaoEncontrado() {
    // Configurar objetos mock
    LocalDateTime dataAcautelamento = LocalDateTime.now();
    String registration = "municipal-guard-registration";
    String armoryKeeperRegistration = "armory-keeper-registration";
    List<ItemAcauteladoDTO> itensAcauteladosRequest = new ArrayList<>();
    itensAcauteladosRequest.add(new ItemAcauteladoDTO(1L, 1L, 2));

    ArmoryKepper armoryKeeperMock = new ArmoryKepper();
    armoryKeeperMock.setRegistrationNumber(armoryKeeperRegistration);

    MunicipalGuard gmMock = new MunicipalGuard();
    gmMock.setRegistrationNumber(registration);

    ItemEstoque itemEstoqueMock = new ItemEstoque();
    Equipament equipamentMock = new Equipament();
    equipamentMock.setId(1L);
    itemEstoqueMock.setEquipament(equipamentMock);
    itemEstoqueMock.setQuantityInStock(5);

    when(armoryKeeperRepository.findByRegistrationNumber(armoryKeeperRegistration)).thenReturn(Optional.empty());

    // Executar o caso de uso
    CriarAcautelamentoDTO request = new CriarAcautelamentoDTO(dataAcautelamento, registration, armoryKeeperRegistration,
        itensAcauteladosRequest);
    assertThrows(ArmoryKeeperNotFoundException.class, () -> sut.execute(request));

    verify(acautelamentoRepository, never()).save(any());
  }

  @Test
  void testCriarAcautelamentoGmNaoEncontrado() {
    // Configurar objetos mock
    LocalDateTime dataAcautelamento = LocalDateTime.now();
    String municipalGuardRegistration = "municipal-guard-registration";
    String armoryKeeperRegistration = "armory-keeper-registration";
    List<ItemAcauteladoDTO> itensAcauteladosRequest = new ArrayList<>();
    itensAcauteladosRequest.add(new ItemAcauteladoDTO(1L, 1L, 2));

    when(armoryKeeperRepository.findByRegistrationNumber(armoryKeeperRegistration))
        .thenReturn(Optional.of(new ArmoryKepper()));
    when(gmRepository.findByRegistrationNumber(municipalGuardRegistration)).thenReturn(Optional.empty());

    // Executar o caso de uso
    CriarAcautelamentoDTO request = new CriarAcautelamentoDTO(dataAcautelamento, municipalGuardRegistration,
        armoryKeeperRegistration,
        itensAcauteladosRequest);

    // check if MunicipalGuardNotFoundException is thrown
    assertThrows(MunicipalGuardNotFoundException.class, () -> sut.execute(request));

    // Verificar que acautelamentoRepository.save() nunca foi chamado
    verify(acautelamentoRepository, never()).save(any());
  }

  @Test
  @Description("Não deve ser possível criar um acautelamento se a quantidade de itens acautelados for maior que a quantidade em estoque")
  void testCriarAcautelamentoQuantidadeInsuficienteNoEstoque() {
    LocalDateTime dataAcautelamento = LocalDateTime.now();
    String municipalGuardRegistration = "municipal-guard-registration";
    String armoryKeeperRegistration = "armory-keeper-registration";
    List<ItemAcauteladoDTO> itensAcauteladosRequest = new ArrayList<>();
    itensAcauteladosRequest.add(new ItemAcauteladoDTO(1L, 1L, 10)); // Quantidade alta para forçar erro

    ArmoryKepper armoryKeeperMock = new ArmoryKepper();
    armoryKeeperMock.setRegistrationNumber(armoryKeeperRegistration);

    MunicipalGuard gmMock = new MunicipalGuard();
    gmMock.setRegistrationNumber(municipalGuardRegistration);

    ItemEstoque itemEstoqueMock = new ItemEstoque();
    Equipament equipamentMock = new Equipament();
    equipamentMock.setId(1L);
    itemEstoqueMock.setEquipament(equipamentMock);
    itemEstoqueMock.setQuantityInStock(5); // low quantity to force error

    when(armoryKeeperRepository.findByRegistrationNumber(armoryKeeperRegistration))
        .thenReturn(Optional.of(armoryKeeperMock));
    when(gmRepository.findByRegistrationNumber(municipalGuardRegistration)).thenReturn(Optional.of(gmMock));
    when(itemEstoqueRepository.diminuirQuantidadeEmEstoque(eq(1L), eq(10))).thenReturn(0); // Retornar 0 para simular
                                                                                           // erro

    // Executar o caso de uso
    CriarAcautelamentoDTO request = new CriarAcautelamentoDTO(dataAcautelamento, municipalGuardRegistration,
        armoryKeeperRegistration,
        itensAcauteladosRequest);

    // check if RuntimeException is thrown
    assertThrows(RuntimeException.class, () -> sut.execute(request));

    // check if acautelamentoRepository.save() was never called
    verify(acautelamentoRepository, never()).save(any());
  }

}
