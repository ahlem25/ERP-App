package com.iss4u.erp.services.modules.achat.domain.commande.dto.taxesfrais.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaxesFraisRequest {
    private String typeDeTaxe;
    private String compteComptable;
    private Double taux;
    private Double montant;


}