package com.example.armaria.use_cases.armory_keeper;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.armaria.entities.ArmoryKepper;
import com.example.armaria.errors.ArmoryKeeperNotFoundException;
import com.example.armaria.repositories.ArmoryKeeperRepository;

@Service
public class DeleteArmoryKeeperUseCase {
  private final ArmoryKeeperRepository armoryKeeperRepository;

  public DeleteArmoryKeeperUseCase(ArmoryKeeperRepository armoryKeeperRepository) {
    this.armoryKeeperRepository = armoryKeeperRepository;
  }

  public void execute(String registration) {
    Optional<ArmoryKepper> optionalArmoryKeeper = armoryKeeperRepository.findByRegistrationNumber(registration);

    if (!optionalArmoryKeeper.isPresent()) {
      throw new ArmoryKeeperNotFoundException("Armeiro não encontrado");
    }

    armoryKeeperRepository.delete(optionalArmoryKeeper.get());
  }
}
