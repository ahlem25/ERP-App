package com.iss4u.erp.services.modules.vente.domain.billing.dto.facturevente.response;

import com.iss4u.erp.services.modules.vente.domain.payment.models.Paiement;
import com.iss4u.erp.services.modules.vente.domain.sales.models.Taxe;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;

import com.iss4u.erp.services.modules.vente.domain.client.models.Client;
import com.iss4u.erp.services.modules.vente.domain.sales.models.Societe;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FactureVenteResponse {
    private Long id;
    private String numero;
    private Date dateEcheance;
    private Float montantTtc;
    private Client client;
    private Societe societe;
    private List<Paiement> paiements;
    private List<Taxe> taxes;


}