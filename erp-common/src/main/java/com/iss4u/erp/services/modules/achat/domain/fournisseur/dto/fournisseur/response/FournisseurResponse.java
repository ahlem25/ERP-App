package com.iss4u.erp.services.modules.achat.domain.fournisseur.dto.fournisseur.response;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FournisseurResponse {
    private Long id;
    private String nomFournisseur;

    private String pays;

    private String typeFournisseur;

    private boolean estTransporteur;

    private String deviseFacturation;

    private String compteBancaireParDefaut;

    private boolean estFournisseurInterne;

    @Column(length = 1000)
    private String detailsFournisseur;

    private String siteWeb;

    private String langueImpression;
    
    // Utiliser des IDs au lieu des entités complètes pour éviter la récursion
    private Long groupeDeFournisseursId;
    private String groupeDeFournisseursNom; // Nom du groupe pour l'affichage
    
    private Long listeDePrixId;
    private String listeDePrixNom; // Nom de la liste de prix pour l'affichage
}