package com.example.armaria.use_cases.armory_keeper;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.armaria.entities.ArmoryKepper;
import com.example.armaria.errors.ArmoryKeeperAlreadyExistsException;
import com.example.armaria.repositories.ArmoryKeeperRepository;
import com.example.armaria.utils.CifradorDeSenha;

@Service
public class CreateArmoryKeeperUseCase {
  private final ArmoryKeeperRepository armoryKeeperRepository;
  private final CifradorDeSenha cifradorDeSenha;

  public CreateArmoryKeeperUseCase(ArmoryKeeperRepository armoryKeeperRepository,
      CifradorDeSenha cifradorDeSenha) {
    this.armoryKeeperRepository = armoryKeeperRepository;
    this.cifradorDeSenha = cifradorDeSenha;
  }

  public void execute(ArmoryKeeperCreateDTO armoryKeeperCreateDTO) {
    Optional<ArmoryKepper> optionalArmoryKeeper = armoryKeeperRepository
        .findByRegistrationNumber(armoryKeeperCreateDTO.registrationNumber());

    if (optionalArmoryKeeper.isPresent()) {
      throw new ArmoryKeeperAlreadyExistsException(armoryKeeperCreateDTO.registrationNumber());
    }

    String hashedPassword = cifradorDeSenha.cifrar(armoryKeeperCreateDTO.password());

    ArmoryKepper armoryKeeper = new ArmoryKepper(armoryKeeperCreateDTO);
    armoryKeeper.setPassword(hashedPassword);

    armoryKeeperRepository.save(armoryKeeper);
  }
}
