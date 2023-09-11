package com.example.armaria.use_cases.armory_keeper;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.armaria.entities.ArmoryKepper;
import com.example.armaria.errors.ArmoryKeeperNotFoundException;
import com.example.armaria.repositories.ArmoryKeeperRepository;

@Service
public class GetArmoryKeeperByRegistrationUseCase {
  private final ArmoryKeeperRepository armoryKeeperRepository;

  @Autowired
  public GetArmoryKeeperByRegistrationUseCase(ArmoryKeeperRepository armoryKeeperRepository) {
    this.armoryKeeperRepository = armoryKeeperRepository;
  }

  public Optional<ArmoryKepper> execute(String registration) {
    Optional<ArmoryKepper> optionalArmoryKeeper = armoryKeeperRepository.findByRegistrationNumber(registration);

    if (!optionalArmoryKeeper.isPresent()) {
      throw new ArmoryKeeperNotFoundException(registration);
    }

    return optionalArmoryKeeper;
  }
}
