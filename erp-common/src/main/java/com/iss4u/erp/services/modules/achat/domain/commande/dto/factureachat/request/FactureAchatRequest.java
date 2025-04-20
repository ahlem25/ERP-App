package com.iss4u.erp.services.modules.achat.domain.commande.dto.factureachat.request;

import com.iss4u.erp.services.modules.achat.domain.commande.models.Item;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

import com.iss4u.erp.services.modules.achat.domain.fournisseur.models.Fournisseur;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FactureAchatRequest {
    private String serie;
    private LocalDate dateDePaiement;
    private Double totalTtc;
    private Fournisseur fournisseur;
    private List<Item> articlesFactures;


}