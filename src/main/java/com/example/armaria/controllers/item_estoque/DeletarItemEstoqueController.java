package com.example.armaria.controllers.item_estoque;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.armaria.use_cases.item_estoque.DeletarItemEstoqueUseCase;

@RestController
@RequestMapping("/api/item-estoque")
public class DeletarItemEstoqueController {
  private final DeletarItemEstoqueUseCase deletarItemEstoqueUseCase;

  public DeletarItemEstoqueController(DeletarItemEstoqueUseCase deletarItemEstoqueUseCase) {
    this.deletarItemEstoqueUseCase = deletarItemEstoqueUseCase;
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Void> handle(@PathVariable Long id) {
    deletarItemEstoqueUseCase.execute(id);
    return ResponseEntity.ok().build();
  }

}
