package com.example.armaria.use_cases.armeiro;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.armaria.entities.Armeiro;
import com.example.armaria.repositories.ArmeiroRepository;

public class AtualizarArmeiroUseCaseTest {
  private AtualizarArmeiroUseCase atualizarArmeiroUseCase;

  @Mock
  private ArmeiroRepository armeiroRepositoryMock;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    atualizarArmeiroUseCase = new AtualizarArmeiroUseCase(armeiroRepositoryMock);
  }

  @Test
  public void testAtualizarArmeiroComSucesso() {
    String matricula = "matricula-existente";

    AtualizarArmeiroDTO novoArmeiroDTO = new AtualizarArmeiroDTO("Novo Nome", "novo@email.com", "Novo Telefone");

    Armeiro armeiroExistente = new Armeiro();
    armeiroExistente.setNome("Nome Existente");
    armeiroExistente.setEmail("email@existente.com");
    armeiroExistente.setTelefone("Telefone Existente");
    armeiroExistente.setSenha("Mesma senha");
    armeiroExistente.setMatricula("Mesma matricula");
    armeiroExistente.setLogin("Mesmo login");

    when(armeiroRepositoryMock.findByMatricula(matricula)).thenReturn(Optional.of(armeiroExistente));

    atualizarArmeiroUseCase.execute(matricula, novoArmeiroDTO);

    verify(armeiroRepositoryMock, times(1)).save(armeiroExistente);
    assertEquals("Novo Nome", armeiroExistente.getNome());
    assertEquals("novo@email.com", armeiroExistente.getEmail());
    assertEquals("Novo Telefone", armeiroExistente.getTelefone());
    assertEquals(armeiroExistente.getSenha(), "Mesma senha");
    assertEquals(armeiroExistente.getMatricula(), "Mesma matricula");
    assertEquals(armeiroExistente.getLogin(), "Mesmo login");
  }
}
