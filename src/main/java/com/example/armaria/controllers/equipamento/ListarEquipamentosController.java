package com.example.armaria.controllers.equipamento;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.armaria.use_cases.equipamento.ListarEquipamentoResponseDTO;
import com.example.armaria.use_cases.equipamento.ListarEquipamentosRequestDTO;
import com.example.armaria.use_cases.equipamento.ListarEquipamentosUseCase;

@RestController
@RequestMapping("/api/equipamento")
@Validated
public class ListarEquipamentosController {
  private final ListarEquipamentosUseCase listarEquipamentosUseCase;

  public ListarEquipamentosController(ListarEquipamentosUseCase listarEquipamentosUseCase) {
    this.listarEquipamentosUseCase = listarEquipamentosUseCase;
  }

  @GetMapping()
  public ResponseEntity<ListarEquipamentoResponseDTO> handle(
      @RequestParam Integer pagina,
      @RequestParam Integer itensPorPagina) {

    ListarEquipamentosRequestDTO dto = new ListarEquipamentosRequestDTO(itensPorPagina, pagina);

    ListarEquipamentoResponseDTO response = listarEquipamentosUseCase.execute(dto);

    return ResponseEntity.ok().body(response);
  }
}