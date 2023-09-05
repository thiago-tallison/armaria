package com.example.armaria.use_cases.armeiro;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.armaria.entities.Armeiro;
import com.example.armaria.errors.ArmeiroNaoEncontradoException;
import com.example.armaria.repositories.ArmeiroRepository;

@Service
public class BuscarArmeiroPorMatriculaUseCase {
  private final ArmeiroRepository repo;

  @Autowired
  public BuscarArmeiroPorMatriculaUseCase(ArmeiroRepository repo) {
    this.repo = repo;
  }

  public Optional<Armeiro> execute(String matricula) {
    Optional<Armeiro> armeiro = repo.findByMatricula(matricula);

    if (!armeiro.isPresent()) {
      throw new ArmeiroNaoEncontradoException(matricula);
    }

    return armeiro;
  }
}
