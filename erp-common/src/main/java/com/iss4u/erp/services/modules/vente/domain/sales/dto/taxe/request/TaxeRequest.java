package com.iss4u.erp.services.modules.vente.domain.sales.dto.taxe.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaxeRequest {
    private String nom;
    private Float taux;
    private String type;


}