package com.iss4u.erp.services.modules.stock.domain.dto.unitemesure.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UniteMesureResponse {
    private Long id;
    private String nom;
    private Boolean estNombreEntier;


}