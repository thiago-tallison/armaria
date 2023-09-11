package com.example.armaria.dtos.devolucao;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DevolverAcautelamentoDTO(
                @NotNull(message = "dataAcautelamento cannot be null") LocalDateTime dataDevolucao,
                @NotBlank(message = "municipalGuardRegistration cannot be null") String municipalGuardRegistration,
                @NotNull(message = "idAcautelamento cannot be null") Long idAcautelamento,
                @NotBlank(message = "armoryKeeperRegistration cannot be null") String armoryKeeperRegistration,
                @NotNull(message = "itensAcautelados cannot be null") List<ItemDevolvidoDTO> itensDevolvidos) {

        public DevolverAcautelamentoDTO withArmoryKeeperRegistration(String armoryKeeperRegistration) {
                return new DevolverAcautelamentoDTO(dataDevolucao, municipalGuardRegistration, idAcautelamento,
                                armoryKeeperRegistration,
                                itensDevolvidos);
        }

        public DevolverAcautelamentoDTO withMunicipalGuardRegistration(String municipalGuardRegistration) {
                return new DevolverAcautelamentoDTO(dataDevolucao, municipalGuardRegistration, idAcautelamento,
                                armoryKeeperRegistration,
                                itensDevolvidos);
        }
}