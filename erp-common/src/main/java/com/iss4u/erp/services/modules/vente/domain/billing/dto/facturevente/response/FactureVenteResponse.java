package com.iss4u.erp.services.modules.vente.domain.billing.dto.facturevente.response;

import com.iss4u.erp.services.modules.vente.domain.billing.models.CommandeClient;
import com.iss4u.erp.services.modules.vente.domain.payment.models.Paiement;
import com.iss4u.erp.services.modules.vente.domain.sales.models.Taxe;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

import com.iss4u.erp.services.modules.vente.domain.client.models.Client;
import com.iss4u.erp.services.modules.vente.domain.sales.models.Societe;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FactureVenteResponse {
    private Long id;
    private String serie;

    private LocalDate date;

    private String heurePublication;

    private Boolean modifierDateHeurePublication;

    private LocalDate dateEcheance;

    private Boolean estPaye;

    private Boolean estRetour;

    private Boolean appliquerRetenueImpot;

    private String numeroFactureFournisseur;

    private LocalDate dateFactureFournisseur;

    private String centreDeCouts;

    private String projet;

    private String devise;

    private String numero;

    private Double montantTtc;


    private List<Paiement> paiements;


    private List<CommandeClient> commandes;
}