package com.example.armaria.presenter.rest.api.equipament;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.armaria.use_cases.equipament.EquipamentsListDTO;
import com.example.armaria.use_cases.equipament.EquipamentsListViewModel;
import com.example.armaria.use_cases.equipament.ListEquipamentsUseCase;

@RestController
@RequestMapping("/api/v1/equipaments")
@Validated
public class ListEquipamentsController {
  private final ListEquipamentsUseCase listEquipamentsUseCase;

  public ListEquipamentsController(ListEquipamentsUseCase listEquipamentsUseCase) {
    this.listEquipamentsUseCase = listEquipamentsUseCase;
  }

  @GetMapping()
  public ResponseEntity<EquipamentsListViewModel> handle(
      @RequestParam Integer pagina,
      @RequestParam Integer itensPorPagina) {

    EquipamentsListDTO equipamentsListDTO = new EquipamentsListDTO(itensPorPagina, pagina);

    EquipamentsListViewModel response = listEquipamentsUseCase.execute(equipamentsListDTO);

    return ResponseEntity.ok().body(response);
  }
}