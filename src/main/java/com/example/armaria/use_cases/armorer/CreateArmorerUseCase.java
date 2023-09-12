package com.example.armaria.use_cases.armorer;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.armaria.core.domain.Armorer;
import com.example.armaria.errors.ArmorerAlreadyExistsException;
import com.example.armaria.repositories.ArmorerRepository;
import com.example.armaria.utils.PasswordEncryptor;

@Service
public class CreateArmorerUseCase {
  private final ArmorerRepository armorerRepository;
  private final PasswordEncryptor passwordEncryptor;

  @Autowired
  public CreateArmorerUseCase(ArmorerRepository armorerRepository,
      PasswordEncryptor passwordEncryptor) {
    this.armorerRepository = armorerRepository;
    this.passwordEncryptor = passwordEncryptor;
  }

  public void execute(ArmorerCreateDTO armorerCreateDTO) {
    Optional<Armorer> optionalArmorer = armorerRepository
        .findByRegistrationNumber(armorerCreateDTO.registrationNumber());

    if (optionalArmorer.isPresent()) {
      throw new ArmorerAlreadyExistsException(armorerCreateDTO.registrationNumber());
    }

    String hashedPassword = passwordEncryptor.cifrar(armorerCreateDTO.password());

    Armorer armorer = new Armorer(armorerCreateDTO);
    armorer.setPassword(hashedPassword);

    armorerRepository.save(armorer);
  }
}
