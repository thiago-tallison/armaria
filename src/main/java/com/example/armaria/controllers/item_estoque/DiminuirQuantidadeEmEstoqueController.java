package com.example.armaria.controllers.item_estoque;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.armaria.use_cases.item_estoque.DiminuirQuantidadeEmEstoqueUseCase;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

record DiminuirQuantidadeEmEstoqueRequest(
    @NotNull(message = "quantidade is required") @Min(value = 1, message = "quantidade must be greater than 0") Integer quantidade) {
}

@RestController
@RequestMapping("/api/item-estoque")
public class DiminuirQuantidadeEmEstoqueController {
  private final DiminuirQuantidadeEmEstoqueUseCase diminuirQuantidadeUseCase;

  @Autowired
  public DiminuirQuantidadeEmEstoqueController(DiminuirQuantidadeEmEstoqueUseCase diminuirQuantidadeUseCase) {
    this.diminuirQuantidadeUseCase = diminuirQuantidadeUseCase;
  }

  @PostMapping("/{id}/diminuir")
  public ResponseEntity<Void> handle(@PathVariable Long id,
      @Valid @RequestBody DiminuirQuantidadeEmEstoqueRequest body) {
    diminuirQuantidadeUseCase.execute(id, body.quantidade());
    return ResponseEntity.ok().build();
  }

}
