package com.example.armaria.use_cases.guarda_municipal;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.armaria.entities.GuardaMunicipal;
import com.example.armaria.repositories.GuardaMunicipalRepository;

@Service
public class BuscarGuardaMunicipalPorMatriculaUseCase {
  private final GuardaMunicipalRepository repo;

  @Autowired
  public BuscarGuardaMunicipalPorMatriculaUseCase(GuardaMunicipalRepository repo) {
    this.repo = repo;
  }

  public Optional<GuardaMunicipal> execute(String matricula) {
    return repo.findByMatricula(matricula);
  }
}
