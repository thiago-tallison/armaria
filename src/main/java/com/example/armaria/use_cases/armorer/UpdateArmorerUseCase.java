package com.example.armaria.use_cases.armorer;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.armaria.core.domain.Armorer;
import com.example.armaria.errors.ArmorerNotFoundException;
import com.example.armaria.repositories.ArmorerRepository;

@Service
public class UpdateArmorerUseCase {
  private final ArmorerRepository armorerRepository;

  @Autowired
  public UpdateArmorerUseCase(ArmorerRepository armorerRepository) {
    this.armorerRepository = armorerRepository;
  }

  public void execute(String registration, ArmorerUpdateDTO armorerUpdateDTO) {
    Optional<Armorer> optionalArmorer = armorerRepository.findByRegistrationNumber(registration);

    if (!optionalArmorer.isPresent()) {
      throw new ArmorerNotFoundException(registration);
    }

    Armorer armorer = optionalArmorer.get();
    armorer.setName(armorerUpdateDTO.name());
    armorer.setEmail(armorerUpdateDTO.email());
    armorer.setPhone(armorerUpdateDTO.phone());
    armorerRepository.save(armorer);
  }

}
