package com.iss4u.erp.services.modules.achat.domain.commande.dto.taxesfrais.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaxesFraisResponse {
    private Long id;
    private String typeDeTaxe;
    private String compteComptable;
    private Double taux;
    private Double montant;


}