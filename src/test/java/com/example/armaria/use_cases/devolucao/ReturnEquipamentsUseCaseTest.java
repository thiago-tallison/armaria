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

import com.example.armaria.core.domain.Armorer;
import com.example.armaria.core.domain.Checkout;
import com.example.armaria.core.domain.Devolucao;
import com.example.armaria.core.domain.MunicipalGuard;
import com.example.armaria.core.domain.StockItem;
import com.example.armaria.core.usecases.armorer.GetArmorerByRegistrationUseCase;
import com.example.armaria.core.usecases.devolucao.ReturnEquipamentRepository;
import com.example.armaria.core.usecases.devolucao.ReturnEquipamentsUseCase;
import com.example.armaria.core.usecases.municipal_guard.GetMunicipalGuardByRegistrationUseCase;
import com.example.armaria.dtos.devolucao.EquipamentReturnDTO;
import com.example.armaria.dtos.devolucao.ItemDevolvidoDTO;
import com.example.armaria.errors.ArmorerNotFoundException;
import com.example.armaria.errors.CheckoutNotFoundException;
import com.example.armaria.errors.MunicipalGuardNotFoundException;
import com.example.armaria.repositories.CheckoutRepository;
import com.example.armaria.repositories.StockItemRepository;

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
  private GetArmorerByRegistrationUseCase getArmorerByRegistrationUseCase;

  @Mock
  private GetMunicipalGuardByRegistrationUseCase getMunicipalGuardByRegistrationUseCase;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void deveCriarDevolucao() {
    EquipamentReturnDTO equipamentReturnDTO = createValidDTO();

    Armorer armorerMock = new Armorer();
    armorerMock.setRegistrationNumber(equipamentReturnDTO.armorerRegistration());

    MunicipalGuard gmMock = new MunicipalGuard();
    gmMock.setRegistrationNumber(equipamentReturnDTO.municipalGuardRegistration());

    Checkout acatuelamentoMock = new Checkout();
    acatuelamentoMock.setId(equipamentReturnDTO.checkoutId());

    StockItem itemEstoqueMock = new StockItem();
    itemEstoqueMock.setId(equipamentReturnDTO.itensDevolvidos().get(0).idItemEstoque());

    Devolucao devolucaoMock = new Devolucao(acatuelamentoMock, equipamentReturnDTO.dataDevolucao(), gmMock,
        armorerMock);

    when(getArmorerByRegistrationUseCase.execute(armorerMock.getRegistrationNumber()))
        .thenReturn(Optional.of(armorerMock));
    when(getMunicipalGuardByRegistrationUseCase.execute(gmMock.getRegistrationNumber()))
        .thenReturn(Optional.of(gmMock));
    when(chcekoutRepository.findById(acatuelamentoMock.getId())).thenReturn(Optional.of(acatuelamentoMock));
    when(stockItemRepository.decreaseStockItemQuantity(itemEstoqueMock.getId(),
        equipamentReturnDTO.itensDevolvidos().get(0).quantidadeDevolvida())).thenReturn(1);
    when(returnEquipamentRepository.save(devolucaoMock)).thenReturn(devolucaoMock);

    assertDoesNotThrow(() -> sut.execute(equipamentReturnDTO));

    verify(stockItemRepository, times(1)).decreaseStockItemQuantity(
        itemEstoqueMock.getId(),
        equipamentReturnDTO.itensDevolvidos().get(0).quantidadeDevolvida());

    verify(returnEquipamentRepository, times(1)).save(any(Devolucao.class));
  }

  @Test
  public void whenRegistrationIsInvalid_ItShouldNotBeAbleToCreateDevolucao() {
    EquipamentReturnDTO dtoMatriculaInvalida = createValidDTO()
        .withArmorerRegistration("inexistent-registration");

    Checkout checkout = createCheckout();

    when(getArmorerByRegistrationUseCase.execute(eq("inexistent-registration")))
        .thenThrow(ArmorerNotFoundException.class);

    when(chcekoutRepository.findById(anyLong())).thenReturn(Optional.of(checkout));

    assertThrows(ArmorerNotFoundException.class, () -> sut.execute(dtoMatriculaInvalida));

    verify(getArmorerByRegistrationUseCase).execute(eq("inexistent-registration"));
    verify(stockItemRepository, never()).decreaseStockItemQuantity(anyLong(), anyInt());
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
    verify(stockItemRepository, never()).decreaseStockItemQuantity(anyLong(), anyInt());
    verify(chcekoutRepository, never()).findById(anyLong());
  }

  @Test
  public void itShouldNotBeAbleToReturnWhenCheckoutIsNotFound() {
    EquipamentReturnDTO equipamentReturnDTO = createValidDTO();

    Armorer armorerMock = new Armorer();
    armorerMock.setRegistrationNumber(equipamentReturnDTO.armorerRegistration());

    MunicipalGuard gmMock = new MunicipalGuard();
    gmMock.setRegistrationNumber(equipamentReturnDTO.municipalGuardRegistration());

    Checkout acatuelamentoMock = new Checkout();
    long idNaoExistente = 1001L;
    acatuelamentoMock.setId(idNaoExistente);

    StockItem itemEstoqueMock = new StockItem();
    itemEstoqueMock.setId(equipamentReturnDTO.itensDevolvidos().get(0).idItemEstoque());

    when(getArmorerByRegistrationUseCase.execute(armorerMock.getRegistrationNumber()))
        .thenReturn(Optional.of(armorerMock));
    when(getMunicipalGuardByRegistrationUseCase.execute(gmMock.getRegistrationNumber()))
        .thenReturn(Optional.of(gmMock));
    when(chcekoutRepository.findById(idNaoExistente)).thenThrow(CheckoutNotFoundException.class);

    assertThrows(CheckoutNotFoundException.class, () -> sut.execute(equipamentReturnDTO));

    verify(stockItemRepository, never()).decreaseStockItemQuantity(
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
    String armorerRegistration = "armorer-registration";
    List<ItemDevolvidoDTO> itensDevolvidos = new ArrayList<>();
    ItemDevolvidoDTO itemDto = new ItemDevolvidoDTO(1L, 2);
    itensDevolvidos.add(itemDto);

    EquipamentReturnDTO dto = new EquipamentReturnDTO(
        checkoutDate,
        municipalguardRegistration,
        checkoutId,
        armorerRegistration,
        itensDevolvidos);

    return dto;
  }
}
