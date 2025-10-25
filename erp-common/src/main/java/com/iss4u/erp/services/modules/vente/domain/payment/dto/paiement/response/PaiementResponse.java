package com.iss4u.erp.services.modules.vente.domain.payment.dto.paiement.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.iss4u.erp.services.modules.vente.domain.billing.models.FactureVente;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaiementResponse {
    private Long id;
    private String type;
    private Float montant;
    private String methodePaiement;
    private FactureVente facture;


}