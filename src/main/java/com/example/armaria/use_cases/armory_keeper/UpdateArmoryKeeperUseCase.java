package com.example.armaria.use_cases.armory_keeper;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.armaria.entities.ArmoryKepper;
import com.example.armaria.errors.ArmoryKeeperNotFoundException;
import com.example.armaria.repositories.ArmoryKeeperRepository;

@Service
public class UpdateArmoryKeeperUseCase {
  private final ArmoryKeeperRepository armoryKeeperRepository;

  @Autowired
  public UpdateArmoryKeeperUseCase(ArmoryKeeperRepository armoryKeeperRepository) {
    this.armoryKeeperRepository = armoryKeeperRepository;
  }

  public void execute(String registration, ArmoryKeeperUpdateDTO armoryKeeperUpdateDTO) {
    Optional<ArmoryKepper> optionalArmoryKeeper = armoryKeeperRepository.findByRegistrationNumber(registration);

    if (!optionalArmoryKeeper.isPresent()) {
      throw new ArmoryKeeperNotFoundException(registration);
    }

    ArmoryKepper armoryKeeper = optionalArmoryKeeper.get();
    armoryKeeper.setName(armoryKeeperUpdateDTO.name());
    armoryKeeper.setEmail(armoryKeeperUpdateDTO.email());
    armoryKeeper.setPhone(armoryKeeperUpdateDTO.phone());
    armoryKeeperRepository.save(armoryKeeper);
  }

}
