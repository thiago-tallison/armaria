package com.example.armaria.use_cases.guarda_municipal;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.armaria.controllers.guarda_municipal.CriarGuardaMunicipalDTO;
import com.example.armaria.entities.GuardaMunicipal;
import com.example.armaria.errors.GuardaMunicipalNaoEncontradoException;
import com.example.armaria.repositories.GuardaMunicipalRepository;

@Service
public class AtualizarGuardaMunicipalUseCase {
  private final GuardaMunicipalRepository guardaMunicipalRepository;

  @Autowired
  public AtualizarGuardaMunicipalUseCase(GuardaMunicipalRepository guardaMunicipalRepository) {
    this.guardaMunicipalRepository = guardaMunicipalRepository;
  }

  public void execute(String matricula, CriarGuardaMunicipalDTO guardaMunicipalDTO) {
    Optional<GuardaMunicipal> guarda = guardaMunicipalRepository.findByMatricula(matricula);

    if (!guarda.isPresent()) {
      throw new GuardaMunicipalNaoEncontradoException(matricula);
    }

    GuardaMunicipal guardaMunicipal = new GuardaMunicipal(guardaMunicipalDTO);
    guardaMunicipalRepository.save(guardaMunicipal);
  }

}
