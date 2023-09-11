package com.example.armaria.use_cases.armory_keeper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.armaria.entities.ArmoryKepper;
import com.example.armaria.repositories.ArmoryKeeperRepository;

public class UpdateArmoryKeeperUseCaseTest {
  private UpdateArmoryKeeperUseCase updateArmoryKeeperUseCase;

  @Mock
  private ArmoryKeeperRepository armoryKeeperRepositoryMock;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    updateArmoryKeeperUseCase = new UpdateArmoryKeeperUseCase(armoryKeeperRepositoryMock);
  }

  @Test
  public void updateArmoryKeeperSuccess() {
    String registration = "inexistent-registration";

    ArmoryKeeperUpdateDTO armoryKeeperUpdateDTO = new ArmoryKeeperUpdateDTO("Novo Nome", "novo@email.com",
        "Novo Telefone");

    ArmoryKepper armoryKeeper = new ArmoryKepper();
    armoryKeeper.setName("ArmoryKeeper 1");
    armoryKeeper.setEmail("email@existente.com");
    armoryKeeper.setPhone("9999");
    armoryKeeper.setRegistrationNumber("same-registration");
    armoryKeeper.setLogin("same-login");
    armoryKeeper.setPassword("same-password");

    when(armoryKeeperRepositoryMock.findByRegistrationNumber(registration)).thenReturn(Optional.of(armoryKeeper));

    updateArmoryKeeperUseCase.execute(registration, armoryKeeperUpdateDTO);

    verify(armoryKeeperRepositoryMock, times(1)).save(armoryKeeper);
    assertEquals("Novo Nome", armoryKeeper.getName());
    assertEquals("novo@email.com", armoryKeeper.getEmail());
    assertEquals("Novo Telefone", armoryKeeper.getPhone());
    assertEquals(armoryKeeper.getPassword(), "same-password");
    assertEquals(armoryKeeper.getRegistrationNumber(), "same-registration");
    assertEquals(armoryKeeper.getLogin(), "same-login");
  }
}
