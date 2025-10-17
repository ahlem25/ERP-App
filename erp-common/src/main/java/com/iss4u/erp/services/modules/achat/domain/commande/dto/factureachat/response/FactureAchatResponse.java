package com.iss4u.erp.services.modules.achat.domain.commande.dto.factureachat.response;

import com.iss4u.erp.services.modules.achat.domain.commande.models.Item;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

import com.iss4u.erp.services.modules.achat.domain.fournisseur.models.Fournisseur;

import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FactureAchatResponse {
    private Long id;
    private String serie;
    private LocalDate date;

    private LocalTime heurePublication;

    private boolean modifierDateHeurePublication;

    private LocalDate dateEcheance;

    private boolean estPaye;

    private boolean estRetour;

    private boolean appliquerRetenueImpot;

    private String numeroFactureFournisseur;

    private LocalDate dateFactureFournisseur;

    private String centreDeCouts;

    private String projet;
    private String devise;
    private Fournisseur fournisseur;



}