package com.example.armaria.dtos.devolucao;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EquipamentReturnDTO(
                @NotNull(message = "dataAcautelamento cannot be null") LocalDateTime dataDevolucao,
                @NotBlank(message = "municipalGuardRegistration cannot be null") String municipalGuardRegistration,
                @NotNull(message = "idAcautelamento cannot be null") Long idAcautelamento,
                @NotBlank(message = "armoryKeeperRegistration cannot be null") String armoryKeeperRegistration,
                @NotNull(message = "itensAcautelados cannot be null") List<ItemDevolvidoDTO> itensDevolvidos) {

        public EquipamentReturnDTO withArmoryKeeperRegistration(String armoryKeeperRegistration) {
                return new EquipamentReturnDTO(dataDevolucao, municipalGuardRegistration, idAcautelamento,
                                armoryKeeperRegistration,
                                itensDevolvidos);
        }

        public EquipamentReturnDTO withMunicipalGuardRegistration(String municipalGuardRegistration) {
                return new EquipamentReturnDTO(dataDevolucao, municipalGuardRegistration, idAcautelamento,
                                armoryKeeperRegistration,
                                itensDevolvidos);
        }
}