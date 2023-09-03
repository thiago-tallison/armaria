package com.example.armaria.use_cases.armeiro;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.armaria.entities.Armeiro;
import com.example.armaria.repositories.ArmeiroRepository;

@Service
public class BuscarArmeiroPorMatriculaUseCase {
  private final ArmeiroRepository repo;

  @Autowired
  public BuscarArmeiroPorMatriculaUseCase(ArmeiroRepository repo) {
    this.repo = repo;
  }

  public Optional<Armeiro> execute(String matricula) {
    return repo.findByMatricula(matricula);
  }
}
