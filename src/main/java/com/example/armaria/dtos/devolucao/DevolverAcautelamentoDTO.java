package com.example.armaria.dtos.devolucao;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DevolverAcautelamentoDTO(
                @NotNull(message = "dataAcautelamento cannot be null") LocalDateTime dataDevolucao,
                @NotBlank(message = "matriculaGm cannot be null") String matriculaGm,
                @NotNull(message = "idAcautelamento cannot be null") Long idAcautelamento,
                @NotBlank(message = "matriculaArmeiro cannot be null") String matriculaArmeiro,
                @NotNull(message = "itensAcautelados cannot be null") List<ItemDevolvidoDTO> itensDevolvidos) {

        public DevolverAcautelamentoDTO withMatriculaArmeiro(String matriculaArmeiro) {
                return new DevolverAcautelamentoDTO(dataDevolucao, matriculaGm, idAcautelamento, matriculaArmeiro,
                                itensDevolvidos);
        }

        public DevolverAcautelamentoDTO withMatriculaGM(String matriculaGm) {
                return new DevolverAcautelamentoDTO(dataDevolucao, matriculaGm, idAcautelamento, matriculaArmeiro,
                                itensDevolvidos);
        }
}