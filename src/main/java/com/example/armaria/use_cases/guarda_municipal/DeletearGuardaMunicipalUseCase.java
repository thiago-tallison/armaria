package com.example.armaria.use_cases.guarda_municipal;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.armaria.entities.GuardaMunicipal;
import com.example.armaria.errors.GuardaMunicipalNaoEncontradoException;
import com.example.armaria.repositories.GuardaMunicipalRepository;

@Service
public class DeletearGuardaMunicipalUseCase {
  private GuardaMunicipalRepository guardaMunicipalRepository;

  @Autowired
  public DeletearGuardaMunicipalUseCase(GuardaMunicipalRepository guardaMunicipalRepository) {
    this.guardaMunicipalRepository = guardaMunicipalRepository;
  }

  public void execute(String matricula) {
    Optional<GuardaMunicipal> armeiro = guardaMunicipalRepository.findByMatricula(matricula);

    if (!armeiro.isPresent()) {
      throw new GuardaMunicipalNaoEncontradoException(matricula);
    }

    guardaMunicipalRepository.delete(armeiro.get());
  }
}
