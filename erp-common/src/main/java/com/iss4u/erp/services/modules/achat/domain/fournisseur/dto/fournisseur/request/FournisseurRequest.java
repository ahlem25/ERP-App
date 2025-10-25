package com.iss4u.erp.services.modules.achat.domain.fournisseur.dto.fournisseur.request;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FournisseurRequest {
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
    
    private Long groupeDeFournisseursId;
    private Long listeDePrixId;
}