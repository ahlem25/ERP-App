package com.iss4u.erp.services.modules.vente.domain.billing.dto.facturevente.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FactureVenteResponse {
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
    
    // Relations avec IDs et noms
    private Long clientId;
    private String clientNom;
    private String clientCode;
    
    private Long societeId;
    private String societeNom;
    private String societeCode;
}