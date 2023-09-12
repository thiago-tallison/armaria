package com.example.armaria.use_cases.checkout;

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

import com.example.armaria.dtos.checkout.CheckoutCreateTDO;
import com.example.armaria.entities.Armorer;
import com.example.armaria.entities.Checkout;
import com.example.armaria.entities.Equipament;
import com.example.armaria.entities.MunicipalGuard;
import com.example.armaria.entities.StockItem;
import com.example.armaria.errors.ArmorerNotFoundException;
import com.example.armaria.errors.MunicipalGuardNotFoundException;
import com.example.armaria.repositories.ArmorerRepository;
import com.example.armaria.repositories.CheckoutRepository;
import com.example.armaria.repositories.MunicipalGuardRepository;
import com.example.armaria.repositories.StockItemRepository;

public class CreatecheckoutUseCaseTest {
  @InjectMocks
  private CreateCheckoutEquipamentUseCase sut;

  @Mock
  private CheckoutRepository checkoutRepository;

  @Mock
  private StockItemRepository stockItemRepository;

  @Mock
  private ArmorerRepository armorerRepository;

  @Mock
  private MunicipalGuardRepository gmRepository;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testCreateCheckoutSucess() {
    // Configurar objetos mock
    LocalDateTime checkoutDate = LocalDateTime.now();
    String registration = "municipal-guard-registration";
    String armorerRegistration = "armorer-registration";
    List<CheckedOutItemDTO> itensAcauteladosRequest = new ArrayList<>();
    itensAcauteladosRequest.add(new CheckedOutItemDTO(1L, 1L, 2));

    Armorer armorerMock = new Armorer();
    armorerMock.setRegistrationNumber(armorerRegistration);

    MunicipalGuard gmMock = new MunicipalGuard();
    gmMock.setRegistrationNumber(registration);

    StockItem itemEstoqueMock = new StockItem();
    Equipament equipamentMock = new Equipament();
    equipamentMock.setId(1L);
    itemEstoqueMock.setEquipament(equipamentMock);
    itemEstoqueMock.setQuantityInStock(5);

    when(armorerRepository.findByRegistrationNumber(armorerRegistration))
        .thenReturn(Optional.of(armorerMock));
    when(gmRepository.findByRegistrationNumber(registration)).thenReturn(Optional.of(gmMock));
    when(stockItemRepository.increaseStockItemQuantity(eq(1L), eq(2))).thenReturn(1);

    // Execute usecase
    CheckoutCreateTDO request = new CheckoutCreateTDO(checkoutDate, registration, armorerRegistration,
        itensAcauteladosRequest);
    assertDoesNotThrow(() -> sut.execute(request));

    // check if checkoutRepository.save() was called
    ArgumentCaptor<Checkout> checkoutCaptor = ArgumentCaptor.forClass(Checkout.class);
    verify(checkoutRepository).save(checkoutCaptor.capture());
    Checkout savedCheckout = checkoutCaptor.getValue();

    assertNotNull(savedCheckout);
    assertEquals(checkoutDate, savedCheckout.getCheckoutDate());
    assertEquals(gmMock, savedCheckout.getGuard());
    assertEquals(armorerMock, savedCheckout.getArmorer());
    assertEquals(1, savedCheckout.getItemsSize());
  }

  @Test
  void testCreateCheckoutWithArmorerNotFound() {
    // Configurar objetos mock
    LocalDateTime checkoutDate = LocalDateTime.now();
    String registration = "municipal-guard-registration";
    String armorerRegistration = "armorer-registration";
    List<CheckedOutItemDTO> itensAcauteladosRequest = new ArrayList<>();
    itensAcauteladosRequest.add(new CheckedOutItemDTO(1L, 1L, 2));

    Armorer armorerMock = new Armorer();
    armorerMock.setRegistrationNumber(armorerRegistration);

    MunicipalGuard gmMock = new MunicipalGuard();
    gmMock.setRegistrationNumber(registration);

    StockItem itemEstoqueMock = new StockItem();
    Equipament equipamentMock = new Equipament();
    equipamentMock.setId(1L);
    itemEstoqueMock.setEquipament(equipamentMock);
    itemEstoqueMock.setQuantityInStock(5);

    when(armorerRepository.findByRegistrationNumber(armorerRegistration)).thenReturn(Optional.empty());

    // Executar o caso de uso
    CheckoutCreateTDO request = new CheckoutCreateTDO(checkoutDate, registration, armorerRegistration,
        itensAcauteladosRequest);
    assertThrows(ArmorerNotFoundException.class, () -> sut.execute(request));

    verify(checkoutRepository, never()).save(any());
  }

  @Test
  void testCreateCheckoutWithMunicialGuardNotFound() {
    // Configurar objetos mock
    LocalDateTime checkoutDate = LocalDateTime.now();
    String municipalGuardRegistration = "municipal-guard-registration";
    String armorerRegistration = "armorer-registration";
    List<CheckedOutItemDTO> itensAcauteladosRequest = new ArrayList<>();
    itensAcauteladosRequest.add(new CheckedOutItemDTO(1L, 1L, 2));

    when(armorerRepository.findByRegistrationNumber(armorerRegistration))
        .thenReturn(Optional.of(new Armorer()));
    when(gmRepository.findByRegistrationNumber(municipalGuardRegistration)).thenReturn(Optional.empty());

    // Executar o caso de uso
    CheckoutCreateTDO request = new CheckoutCreateTDO(checkoutDate, municipalGuardRegistration,
        armorerRegistration,
        itensAcauteladosRequest);

    // check if MunicipalGuardNotFoundException is thrown
    assertThrows(MunicipalGuardNotFoundException.class, () -> sut.execute(request));

    // check if checkoutRepository.save() was never called
    verify(checkoutRepository, never()).save(any());
  }

  @Test
  @Description("It should not be able to create a checkout when the quantity of items in stock is insuficient")
  void testCreateCheckoutWithInsuficientStockItemQuantity() {
    LocalDateTime checkoutDate = LocalDateTime.now();
    String municipalGuardRegistration = "municipal-guard-registration";
    String armorerRegistration = "armorer-registration";
    List<CheckedOutItemDTO> itensAcauteladosRequest = new ArrayList<>();
    itensAcauteladosRequest.add(new CheckedOutItemDTO(1L, 1L, 10)); // Quantidade alta para forçar erro

    Armorer armorerMock = new Armorer();
    armorerMock.setRegistrationNumber(armorerRegistration);

    MunicipalGuard gmMock = new MunicipalGuard();
    gmMock.setRegistrationNumber(municipalGuardRegistration);

    StockItem itemEstoqueMock = new StockItem();
    Equipament equipamentMock = new Equipament();
    equipamentMock.setId(1L);
    itemEstoqueMock.setEquipament(equipamentMock);
    itemEstoqueMock.setQuantityInStock(5); // low quantity to force error

    when(armorerRepository.findByRegistrationNumber(armorerRegistration))
        .thenReturn(Optional.of(armorerMock));
    when(gmRepository.findByRegistrationNumber(municipalGuardRegistration)).thenReturn(Optional.of(gmMock));
    when(stockItemRepository.increaseStockItemQuantity(eq(1L), eq(10))).thenReturn(0); // Retornar 0 para simular
                                                                                       // erro

    // Executar o caso de uso
    CheckoutCreateTDO request = new CheckoutCreateTDO(checkoutDate, municipalGuardRegistration,
        armorerRegistration,
        itensAcauteladosRequest);

    // check if RuntimeException is thrown
    assertThrows(RuntimeException.class, () -> sut.execute(request));

    // check if checkoutRepository.save() was never called
    verify(checkoutRepository, never()).save(any());
  }

}
