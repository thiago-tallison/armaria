package com.example.armaria.use_cases.acautelamento;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.armaria.entities.Acautelamento;
import com.example.armaria.entities.Armeiro;
import com.example.armaria.entities.GuardaMunicipal;
import com.example.armaria.entities.ItemEstoque;
import com.example.armaria.errors.ArmeiroNaoEncontradoException;
import com.example.armaria.errors.GuardaMunicipalNaoEncontradoException;
import com.example.armaria.errors.ItemEstoqueNaoEncontradoException;
import com.example.armaria.repositories.ArmeiroRepository;
import com.example.armaria.repositories.GuardaMunicipalRepository;
import com.example.armaria.repositories.ItemEstoqueRepository;

public class CriarAcautelamentoUseCaseTest {
  @InjectMocks
  private CriarAcautelamentoUseCase criarAcautelamentoUseCase;

  @Mock
  private AcautelamentoRepository acautelamentoRepository;

  @Mock
  private ItemEstoqueRepository itemEstoqueRepository;

  @Mock
  private ArmeiroRepository armeiroRepository;

  @Mock
  private GuardaMunicipalRepository gmRepository;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testCriarAcautelamento() {
    String matriculaArmeiro = "matricula-1";
    String matriculaGm = "matricula-2";
    List<ItemAcauteladoRequest> itensAcautelados = new ArrayList<>();

    Armeiro armeiro = new Armeiro();
    armeiro.setMatricula(matriculaArmeiro);

    GuardaMunicipal guardaMunicipal = new GuardaMunicipal();
    guardaMunicipal.setMatricula(matriculaGm);

    for (int i = 0; i < 2; i++) {
      ItemAcauteladoRequest itemRequest = new ItemAcauteladoRequest((long) (i + 1), "equipamento-" + i, 5);
      itensAcautelados.add(itemRequest);

      ItemEstoque itemEstoque = new ItemEstoque();
      itemEstoque.setId(itemRequest.id());
      itemEstoque.setQuantidadeEmEstoque(10);

      when(itemEstoqueRepository.existeEQuantidadeDisponivelEmEstoque(itemRequest.id(),
          itemRequest.quantidadeAcautelada()))
          .thenReturn(Optional.of(itemEstoque));
    }

    when(armeiroRepository.findByMatricula(matriculaArmeiro)).thenReturn(Optional.of(armeiro));
    when(gmRepository.findByMatricula(matriculaGm)).thenReturn(Optional.of(guardaMunicipal));

    // Execute o caso de uso
    CriarAcautelamentoRequest request = new CriarAcautelamentoRequest(LocalDateTime.now(), matriculaGm,
        matriculaArmeiro, itensAcautelados);
    criarAcautelamentoUseCase.execute(request);

    // Verifique se o acautelamento foi criado corretamente
    verify(acautelamentoRepository).save(any(Acautelamento.class));

    // Verifique se o estoque foi atualizado corretamente
    for (ItemAcauteladoRequest itemRequest : itensAcautelados) {
      verify(itemEstoqueRepository).diminuirQuantidadeEmEstoque(itemRequest.id(), itemRequest.quantidadeAcautelada());
    }
  }

  @Test()
  public void testErroArmeiroNaoEncontrado() {
    String matriculaArmeiro = "matricula-1";
    String matriculaGm = "matricula-2";
    List<ItemAcauteladoRequest> itensAcautelados = new ArrayList<>();

    when(armeiroRepository.findByMatricula(matriculaArmeiro)).thenReturn(Optional.empty());

    CriarAcautelamentoRequest request = new CriarAcautelamentoRequest(LocalDateTime.now(), matriculaGm,
        matriculaArmeiro, itensAcautelados);

    assertThrows(ArmeiroNaoEncontradoException.class, () -> {
      criarAcautelamentoUseCase.execute(request);
    });
  }

  @Test()
  public void testErroGuardaMunicipalNaoEncontrado() {
    String matriculaArmeiro = "matricula-1";
    String matriculaGm = "matricula-2";
    List<ItemAcauteladoRequest> itensAcautelados = new ArrayList<>();

    when(armeiroRepository.findByMatricula(matriculaArmeiro)).thenReturn(Optional.of(new Armeiro()));
    when(gmRepository.findByMatricula(matriculaGm)).thenReturn(Optional.empty());

    CriarAcautelamentoRequest request = new CriarAcautelamentoRequest(LocalDateTime.now(), matriculaGm,
        matriculaArmeiro, itensAcautelados);

    assertThrows(GuardaMunicipalNaoEncontradoException.class, () -> {
      criarAcautelamentoUseCase.execute(request);
    });
  }

  @Test()
  public void testErroItemEstoqueNaoEncontrado() {
    String matriculaArmeiro = "matricula-1";
    String matriculaGm = "matricula-2";
    List<ItemAcauteladoRequest> itensAcautelados = new ArrayList<>();
    ItemAcauteladoRequest itemRequest = new ItemAcauteladoRequest(1L, "equipamento-1", 5);
    itensAcautelados.add(itemRequest);

    when(armeiroRepository.findByMatricula(matriculaArmeiro)).thenReturn(Optional.of(new Armeiro()));
    when(gmRepository.findByMatricula(matriculaGm)).thenReturn(Optional.of(new GuardaMunicipal()));
    when(itemEstoqueRepository.existeEQuantidadeDisponivelEmEstoque(itemRequest.id(),
        itemRequest.quantidadeAcautelada()))
        .thenReturn(Optional.empty());

    CriarAcautelamentoRequest request = new CriarAcautelamentoRequest(LocalDateTime.now(), matriculaGm,
        matriculaArmeiro, itensAcautelados);

    assertThrows(ItemEstoqueNaoEncontradoException.class, () -> {
      criarAcautelamentoUseCase.execute(request);
    });
  }
}
