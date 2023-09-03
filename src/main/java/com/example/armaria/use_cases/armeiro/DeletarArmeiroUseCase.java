package com.example.armaria.use_cases.armeiro;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.armaria.entities.Armeiro;
import com.example.armaria.errors.ArmeiroNaoEncontradoException;
import com.example.armaria.repositories.ArmeiroRepository;

@Service
public class DeletarArmeiroUseCase {
  private final ArmeiroRepository armeiroRepository;

  @Autowired
  public DeletarArmeiroUseCase(ArmeiroRepository armeiroRepository) {
    this.armeiroRepository = armeiroRepository;
  }

  public void execute(String matricula) {
    Optional<Armeiro> armeiro = armeiroRepository.findByMatricula(matricula);

    if (!armeiro.isPresent()) {
      throw new ArmeiroNaoEncontradoException("Armeiro não encontrado");
    }

    armeiroRepository.delete(armeiro.get());
  }
}
