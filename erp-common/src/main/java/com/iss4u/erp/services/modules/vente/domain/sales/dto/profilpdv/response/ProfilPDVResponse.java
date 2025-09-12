package com.iss4u.erp.services.modules.vente.domain.sales.dto.profilpdv.response;

import com.iss4u.erp.services.modules.vente.domain.payment.models.MethodePaiement;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.iss4u.erp.services.modules.vente.domain.sales.models.Societe;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfilPDVResponse {
    private Long id;
    private String nom;
    private String methodesPaiementAutorisees;
    private String entrepotAssocie;
    private String parametresImpression;
    private Societe societe;
    private List<MethodePaiement> methodesPaiement;


}