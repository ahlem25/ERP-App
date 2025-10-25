package com.iss4u.erp.services.modules.vente.domain.billing.dto.facturevente.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FactureVenteRequest {
    private Long id;
    private String numero;
    private String serie;
    private LocalDate date;
    private LocalTime heure;
    private LocalDate dateEcheance;
    private String statut;
    private String reference;
    private String commentaire;
    private String devise;
    private BigDecimal montantHt;
    private BigDecimal montantTtc;
    private BigDecimal montantTva;
    private BigDecimal montantRemise;
    private BigDecimal montantTotal;
    private boolean estPaye;
    private boolean estRetour;
    private String numeroFactureClient;
    private LocalDate dateFactureClient;
    private String centreDeCouts;
    private String projet;
    
    // Relations avec IDs seulement
    private Long clientId;
    private Long societeId;
}