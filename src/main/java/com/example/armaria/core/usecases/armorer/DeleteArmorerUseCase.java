package com.example.armaria.core.usecases.armorer;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.armaria.core.domain.Armorer;
import com.example.armaria.errors.ArmorerNotFoundException;
import com.example.armaria.repositories.ArmorerRepository;

@Service
public class DeleteArmorerUseCase {
  private final ArmorerRepository armorerRepository;

  @Autowired
  public DeleteArmorerUseCase(ArmorerRepository armorerRepository) {
    this.armorerRepository = armorerRepository;
  }

  public void execute(String registration) {
    Optional<Armorer> optionalArmorer = armorerRepository.findByRegistrationNumber(registration);

    if (!optionalArmorer.isPresent()) {
      throw new ArmorerNotFoundException("Armeiro não encontrado");
    }

    armorerRepository.delete(optionalArmorer.get());
  }
}
