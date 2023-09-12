package com.example.armaria.use_cases.stock_item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.armaria.entities.Equipament;
import com.example.armaria.entities.ItemEstoque;
import com.example.armaria.repositories.ItemEstoqueRepository;
import com.example.armaria.use_cases.equipament.EquipamentCreateDTO;

public class AumentarQuantidadeEmEstoqueUseCaseTest {

  @Mock
  private ItemEstoqueRepository itemEstoqueRepositoryMock;

  @InjectMocks
  private IncreaseStockItemQuantityUseCase aumentarQuantidadeEmEstoqueUseCase;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testExecute() {
    int quatidadeEmEstoque = 10;
    int quantidadeParaAumentar = 10;

    EquipamentCreateDTO dto = new EquipamentCreateDTO("Equipament 1", "serial-number-1", true,
        quatidadeEmEstoque);

    Equipament e1 = new Equipament(dto);

    ItemEstoque itemEstoque = new ItemEstoque(e1, quatidadeEmEstoque);
    itemEstoque.setId(1L);

    when(itemEstoqueRepositoryMock.save(itemEstoque)).thenReturn(null);
    when(itemEstoqueRepositoryMock.findById(1L)).thenReturn(Optional.ofNullable(itemEstoque));

    aumentarQuantidadeEmEstoqueUseCase.execute(1L, quantidadeParaAumentar);

    assertEquals(20, itemEstoqueRepositoryMock.findById(1L).get().getQuantityInStock());
  }
}
