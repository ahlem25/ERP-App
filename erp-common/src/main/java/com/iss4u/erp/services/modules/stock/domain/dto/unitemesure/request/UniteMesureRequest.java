package com.iss4u.erp.services.modules.stock.domain.dto.unitemesure.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UniteMesureRequest {
    private String nom;
    private Boolean estNombreEntier;


}