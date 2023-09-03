package com.example.armaria.use_cases.armeiro;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.armaria.entities.Armeiro;
import com.example.armaria.errors.ArmeiroNaoEncontradoException;
import com.example.armaria.repositories.ArmeiroRepository;

@Service
public class AtualizarArmeiroUseCase {
  private final ArmeiroRepository armeiroRepository;

  @Autowired
  public AtualizarArmeiroUseCase(ArmeiroRepository armeiroRepository) {
    this.armeiroRepository = armeiroRepository;
  }

  public void execute(String matricula, AtualizarArmeiroDTO armeiroDto) {
    Optional<Armeiro> estaCadastrado = armeiroRepository.findByMatricula(matricula);

    if (!estaCadastrado.isPresent()) {
      throw new ArmeiroNaoEncontradoException(matricula);
    }

    Armeiro armeiro = estaCadastrado.get();
    armeiro.setNome(armeiroDto.nome());
    armeiro.setEmail(armeiroDto.email());
    armeiro.setTelefone(armeiroDto.telefone());
    armeiroRepository.save(armeiro);
  }

}
