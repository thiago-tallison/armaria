package com.example.armaria.use_cases.armorer;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.armaria.entities.Armorer;
import com.example.armaria.errors.ArmorerNotFoundException;
import com.example.armaria.repositories.ArmorerRepository;

@Service
public class GetArmorerByRegistrationUseCase {
  private final ArmorerRepository armorerRepository;

  @Autowired
  public GetArmorerByRegistrationUseCase(ArmorerRepository armorerRepository) {
    this.armorerRepository = armorerRepository;
  }

  public Optional<Armorer> execute(String registration) {
    Optional<Armorer> optionalArmorer = armorerRepository.findByRegistrationNumber(registration);

    if (!optionalArmorer.isPresent()) {
      throw new ArmorerNotFoundException(registration);
    }

    return optionalArmorer;
  }
}
