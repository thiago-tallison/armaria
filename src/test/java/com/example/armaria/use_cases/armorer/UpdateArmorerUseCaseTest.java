package com.example.armaria.use_cases.armorer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.armaria.core.domain.Armorer;
import com.example.armaria.core.usecases.armorer.ArmorerUpdateDTO;
import com.example.armaria.core.usecases.armorer.UpdateArmorerUseCase;
import com.example.armaria.repositories.ArmorerRepository;

public class UpdateArmorerUseCaseTest {
  private UpdateArmorerUseCase updateArmorerUseCase;

  @Mock
  private ArmorerRepository armorerRepositoryMock;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    updateArmorerUseCase = new UpdateArmorerUseCase(armorerRepositoryMock);
  }

  @Test
  public void updateArmorerSuccess() {
    String registration = "inexistent-registration";

    ArmorerUpdateDTO armorerUpdateDTO = new ArmorerUpdateDTO("Novo Nome", "novo@email.com",
        "Novo Telefone");

    Armorer armorer = new Armorer();
    armorer.setName("Armorer 1");
    armorer.setEmail("email@existente.com");
    armorer.setPhone("9999");
    armorer.setRegistrationNumber("same-registration");
    armorer.setLogin("same-login");
    armorer.setPassword("same-password");

    when(armorerRepositoryMock.findByRegistrationNumber(registration)).thenReturn(Optional.of(armorer));

    updateArmorerUseCase.execute(registration, armorerUpdateDTO);

    verify(armorerRepositoryMock, times(1)).save(armorer);
    assertEquals("Novo Nome", armorer.getName());
    assertEquals("novo@email.com", armorer.getEmail());
    assertEquals("Novo Telefone", armorer.getPhone());
    assertEquals(armorer.getPassword(), "same-password");
    assertEquals(armorer.getRegistrationNumber(), "same-registration");
    assertEquals(armorer.getLogin(), "same-login");
  }
}
