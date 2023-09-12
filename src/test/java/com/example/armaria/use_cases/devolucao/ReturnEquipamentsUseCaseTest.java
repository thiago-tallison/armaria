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
import com.example.armaria.entities.ArmoryKepper;
import com.example.armaria.entities.Checkout;
import com.example.armaria.entities.Devolucao;
import com.example.armaria.entities.MunicipalGuard;
import com.example.armaria.entities.StockItem;
import com.example.armaria.errors.ArmoryKeeperNotFoundException;
import com.example.armaria.errors.CheckoutNotFoundException;
import com.example.armaria.errors.MunicipalGuardNotFoundException;
import com.example.armaria.repositories.CheckoutRepository;
import com.example.armaria.repositories.StockItemRepository;
import com.example.armaria.use_cases.armory_keeper.GetArmoryKeeperByRegistrationUseCase;
import com.example.armaria.use_cases.municipal_guard.GetMunicipalGuardByRegistrationUseCase;

public class ReturnEquipamentsUseCaseTest {
  @InjectMocks
  ReturnEquipamentsUseCase sut;

  @Mock
  private CheckoutRepository chcekoutRepository;

  @Mock
  private ReturnEquipamentRepository returnEquipamentRepository;

  @Mock
  private StockItemRepository stockItemRepository;

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

    Checkout acatuelamentoMock = new Checkout();
    acatuelamentoMock.setId(equipamentReturnDTO.checkoutId());

    StockItem itemEstoqueMock = new StockItem();
    itemEstoqueMock.setId(equipamentReturnDTO.itensDevolvidos().get(0).idItemEstoque());

    Devolucao devolucaoMock = new Devolucao(acatuelamentoMock, equipamentReturnDTO.dataDevolucao(), gmMock,
        armoryKeeperMock);

    when(getArmoryKeeperByRegistrationUseCase.execute(armoryKeeperMock.getRegistrationNumber()))
        .thenReturn(Optional.of(armoryKeeperMock));
    when(getMunicipalGuardByRegistrationUseCase.execute(gmMock.getRegistrationNumber()))
        .thenReturn(Optional.of(gmMock));
    when(chcekoutRepository.findById(acatuelamentoMock.getId())).thenReturn(Optional.of(acatuelamentoMock));
    when(stockItemRepository.aumentarQuantidadeEmEstoque(itemEstoqueMock.getId(),
        equipamentReturnDTO.itensDevolvidos().get(0).quantidadeDevolvida())).thenReturn(1);
    when(returnEquipamentRepository.save(devolucaoMock)).thenReturn(devolucaoMock);

    assertDoesNotThrow(() -> sut.execute(equipamentReturnDTO));

    verify(stockItemRepository, times(1)).aumentarQuantidadeEmEstoque(
        itemEstoqueMock.getId(),
        equipamentReturnDTO.itensDevolvidos().get(0).quantidadeDevolvida());

    verify(returnEquipamentRepository, times(1)).save(any(Devolucao.class));
  }

  @Test
  public void whenRegistrationIsInvalid_ItShouldNotBeAbleToCreateDevolucao() {
    EquipamentReturnDTO dtoMatriculaInvalida = createValidDTO()
        .withArmoryKeeperRegistration("inexistent-registration");

    Checkout checkout = createCheckout();

    when(getArmoryKeeperByRegistrationUseCase.execute(eq("inexistent-registration")))
        .thenThrow(ArmoryKeeperNotFoundException.class);

    when(chcekoutRepository.findById(anyLong())).thenReturn(Optional.of(checkout));

    assertThrows(ArmoryKeeperNotFoundException.class, () -> sut.execute(dtoMatriculaInvalida));

    verify(getArmoryKeeperByRegistrationUseCase).execute(eq("inexistent-registration"));
    verify(stockItemRepository, never()).aumentarQuantidadeEmEstoque(anyLong(), anyInt());
    verify(chcekoutRepository, never()).findById(anyLong());
  }

  @Test
  public void naoDeveCriarDevolucaoGMMatriculaInvalida() {
    EquipamentReturnDTO dtoMatriculaInvalida = createValidDTO()
        .withMunicipalGuardRegistration("inexistent-registration");

    Checkout checkout = createCheckout();

    when(getMunicipalGuardByRegistrationUseCase.execute(eq("inexistent-registration")))
        .thenThrow(MunicipalGuardNotFoundException.class);
    when(chcekoutRepository.findById(anyLong())).thenReturn(Optional.of(checkout));

    assertThrows(MunicipalGuardNotFoundException.class, () -> sut.execute(dtoMatriculaInvalida));

    verify(getMunicipalGuardByRegistrationUseCase).execute(eq("inexistent-registration"));
    verify(stockItemRepository, never()).aumentarQuantidadeEmEstoque(anyLong(), anyInt());
    verify(chcekoutRepository, never()).findById(anyLong());
  }

  @Test
  public void itShouldNotBeAbleToReturnWhenCheckoutIsNotFound() {
    EquipamentReturnDTO equipamentReturnDTO = createValidDTO();

    ArmoryKepper armoryKeeperMock = new ArmoryKepper();
    armoryKeeperMock.setRegistrationNumber(equipamentReturnDTO.armoryKeeperRegistration());

    MunicipalGuard gmMock = new MunicipalGuard();
    gmMock.setRegistrationNumber(equipamentReturnDTO.municipalGuardRegistration());

    Checkout acatuelamentoMock = new Checkout();
    long idNaoExistente = 1001L;
    acatuelamentoMock.setId(idNaoExistente);

    StockItem itemEstoqueMock = new StockItem();
    itemEstoqueMock.setId(equipamentReturnDTO.itensDevolvidos().get(0).idItemEstoque());

    when(getArmoryKeeperByRegistrationUseCase.execute(armoryKeeperMock.getRegistrationNumber()))
        .thenReturn(Optional.of(armoryKeeperMock));
    when(getMunicipalGuardByRegistrationUseCase.execute(gmMock.getRegistrationNumber()))
        .thenReturn(Optional.of(gmMock));
    when(chcekoutRepository.findById(idNaoExistente)).thenThrow(CheckoutNotFoundException.class);

    assertThrows(CheckoutNotFoundException.class, () -> sut.execute(equipamentReturnDTO));

    verify(stockItemRepository, never()).aumentarQuantidadeEmEstoque(
        anyLong(),
        anyInt());

    verify(returnEquipamentRepository, never()).save(any(Devolucao.class));
  }

  private Checkout createCheckout() {
    return new Checkout();
  }

  private EquipamentReturnDTO createValidDTO() {
    // given
    LocalDateTime checkoutDate = LocalDateTime.now();
    Long checkoutId = 1L;
    String municipalguardRegistration = "municipal-guard-registration";
    String armoryKeeperRegistration = "armory-keeper-registration";
    List<ItemDevolvidoDTO> itensDevolvidos = new ArrayList<>();
    ItemDevolvidoDTO itemDto = new ItemDevolvidoDTO(1L, 2);
    itensDevolvidos.add(itemDto);

    EquipamentReturnDTO dto = new EquipamentReturnDTO(
        checkoutDate,
        municipalguardRegistration,
        checkoutId,
        armoryKeeperRegistration,
        itensDevolvidos);

    return dto;
  }
}
