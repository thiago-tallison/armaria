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

import com.example.armaria.dtos.devolucao.EquipamentReturnDTO;
import com.example.armaria.dtos.devolucao.ItemDevolvidoDTO;
import com.example.armaria.entities.Acautelamento;
import com.example.armaria.entities.ArmoryKepper;
import com.example.armaria.entities.Devolucao;
import com.example.armaria.entities.ItemEstoque;
import com.example.armaria.entities.MunicipalGuard;
import com.example.armaria.errors.AcautelamentoNaoEncontradoException;
import com.example.armaria.errors.ArmoryKeeperNotFoundException;
import com.example.armaria.errors.MunicipalGuardNotFoundException;
import com.example.armaria.repositories.AcautelamentoRepository;
import com.example.armaria.repositories.ItemEstoqueRepository;
import com.example.armaria.use_cases.armory_keeper.GetArmoryKeeperByRegistrationUseCase;
import com.example.armaria.use_cases.municipal_guard.GetMunicipalGuardByRegistrationUseCase;

public class DevolverAcautelamentoUseCaseTest {
  @InjectMocks
  DevolverAcautelamentoUseCase sut;

  @Mock
  private AcautelamentoRepository acautelamentoRepository;

  @Mock
  private ReturnEquipamentRepository returnEquipamentRepository;

  @Mock
  private ItemEstoqueRepository itemEstoqueRepository;

  @Mock
  private GetArmoryKeeperByRegistrationUseCase getArmoryKeeperByRegistrationUseCase;

  @Mock
  private GetMunicipalGuardByRegistrationUseCase getMunicipalGuardByRegistrationUseCase;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void deveCriarDevolucao() {
    EquipamentReturnDTO equipamentReturnDTO = createValidDTO();

    ArmoryKepper armoryKeeperMock = new ArmoryKepper();
    armoryKeeperMock.setRegistrationNumber(equipamentReturnDTO.armoryKeeperRegistration());

    MunicipalGuard gmMock = new MunicipalGuard();
    gmMock.setRegistrationNumber(equipamentReturnDTO.municipalGuardRegistration());

    Acautelamento acatuelamentoMock = new Acautelamento();
    acatuelamentoMock.setId(equipamentReturnDTO.idAcautelamento());

    ItemEstoque itemEstoqueMock = new ItemEstoque();
    itemEstoqueMock.setId(equipamentReturnDTO.itensDevolvidos().get(0).idItemEstoque());

    Devolucao devolucaoMock = new Devolucao(acatuelamentoMock, equipamentReturnDTO.dataDevolucao(), gmMock,
        armoryKeeperMock);

    when(getArmoryKeeperByRegistrationUseCase.execute(armoryKeeperMock.getRegistrationNumber()))
        .thenReturn(Optional.of(armoryKeeperMock));
    when(getMunicipalGuardByRegistrationUseCase.execute(gmMock.getRegistrationNumber()))
        .thenReturn(Optional.of(gmMock));
    when(acautelamentoRepository.findById(acatuelamentoMock.getId())).thenReturn(Optional.of(acatuelamentoMock));
    when(itemEstoqueRepository.aumentarQuantidadeEmEstoque(itemEstoqueMock.getId(),
        equipamentReturnDTO.itensDevolvidos().get(0).quantidadeDevolvida())).thenReturn(1);
    when(returnEquipamentRepository.save(devolucaoMock)).thenReturn(devolucaoMock);

    assertDoesNotThrow(() -> sut.execute(equipamentReturnDTO));

    verify(itemEstoqueRepository, times(1)).aumentarQuantidadeEmEstoque(
        itemEstoqueMock.getId(),
        equipamentReturnDTO.itensDevolvidos().get(0).quantidadeDevolvida());

    verify(returnEquipamentRepository, times(1)).save(any(Devolucao.class));
  }

  @Test
  public void whenRegistrationIsInvalid_ItShouldNotBeAbleToCreateDevolucao() {
    EquipamentReturnDTO dtoMatriculaInvalida = createValidDTO()
        .withArmoryKeeperRegistration("inexistent-registration");

    Acautelamento acautelamento = createAcautelamento();

    when(getArmoryKeeperByRegistrationUseCase.execute(eq("inexistent-registration")))
        .thenThrow(ArmoryKeeperNotFoundException.class);

    when(acautelamentoRepository.findById(anyLong())).thenReturn(Optional.of(acautelamento));

    assertThrows(ArmoryKeeperNotFoundException.class, () -> sut.execute(dtoMatriculaInvalida));

    verify(getArmoryKeeperByRegistrationUseCase).execute(eq("inexistent-registration"));
    verify(itemEstoqueRepository, never()).aumentarQuantidadeEmEstoque(anyLong(), anyInt());
    verify(acautelamentoRepository, never()).findById(anyLong());
  }

  @Test
  public void naoDeveCriarDevolucaoGMMatriculaInvalida() {
    EquipamentReturnDTO dtoMatriculaInvalida = createValidDTO()
        .withMunicipalGuardRegistration("inexistent-registration");

    Acautelamento acautelamento = createAcautelamento();

    when(getMunicipalGuardByRegistrationUseCase.execute(eq("inexistent-registration")))
        .thenThrow(MunicipalGuardNotFoundException.class);
    when(acautelamentoRepository.findById(anyLong())).thenReturn(Optional.of(acautelamento));

    assertThrows(MunicipalGuardNotFoundException.class, () -> sut.execute(dtoMatriculaInvalida));

    verify(getMunicipalGuardByRegistrationUseCase).execute(eq("inexistent-registration"));
    verify(itemEstoqueRepository, never()).aumentarQuantidadeEmEstoque(anyLong(), anyInt());
    verify(acautelamentoRepository, never()).findById(anyLong());
  }

  @Test
  public void naoDeveCriarDevolucaoAcautelamentoNaoExistente() {
    EquipamentReturnDTO equipamentReturnDTO = createValidDTO();

    ArmoryKepper armoryKeeperMock = new ArmoryKepper();
    armoryKeeperMock.setRegistrationNumber(equipamentReturnDTO.armoryKeeperRegistration());

    MunicipalGuard gmMock = new MunicipalGuard();
    gmMock.setRegistrationNumber(equipamentReturnDTO.municipalGuardRegistration());

    Acautelamento acatuelamentoMock = new Acautelamento();
    long idNaoExistente = 1001L;
    acatuelamentoMock.setId(idNaoExistente);

    ItemEstoque itemEstoqueMock = new ItemEstoque();
    itemEstoqueMock.setId(equipamentReturnDTO.itensDevolvidos().get(0).idItemEstoque());

    when(getArmoryKeeperByRegistrationUseCase.execute(armoryKeeperMock.getRegistrationNumber()))
        .thenReturn(Optional.of(armoryKeeperMock));
    when(getMunicipalGuardByRegistrationUseCase.execute(gmMock.getRegistrationNumber()))
        .thenReturn(Optional.of(gmMock));
    when(acautelamentoRepository.findById(idNaoExistente)).thenThrow(AcautelamentoNaoEncontradoException.class);

    assertThrows(AcautelamentoNaoEncontradoException.class, () -> sut.execute(equipamentReturnDTO));

    verify(itemEstoqueRepository, never()).aumentarQuantidadeEmEstoque(
        anyLong(),
        anyInt());

    verify(returnEquipamentRepository, never()).save(any(Devolucao.class));
  }

  private Acautelamento createAcautelamento() {
    return new Acautelamento();
  }

  private EquipamentReturnDTO createValidDTO() {
    // given
    LocalDateTime dataAcautelamento = LocalDateTime.now();
    Long idAcautelamento = 1L;
    String municipalguardRegistration = "municipal-guard-registration";
    String armoryKeeperRegistration = "armory-keeper-registration";
    List<ItemDevolvidoDTO> itensDevolvidos = new ArrayList<>();
    ItemDevolvidoDTO itemDto = new ItemDevolvidoDTO(1L, 2);
    itensDevolvidos.add(itemDto);

    EquipamentReturnDTO dto = new EquipamentReturnDTO(
        dataAcautelamento,
        municipalguardRegistration,
        idAcautelamento,
        armoryKeeperRegistration,
        itensDevolvidos);

    return dto;
  }
}
