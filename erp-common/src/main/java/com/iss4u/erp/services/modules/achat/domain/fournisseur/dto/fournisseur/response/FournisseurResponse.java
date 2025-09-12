package com.iss4u.erp.services.modules.achat.domain.fournisseur.dto.fournisseur.response;

import com.iss4u.erp.services.modules.achat.domain.commande.models.FactureAchat;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.iss4u.erp.services.modules.achat.domain.fournisseur.models.GroupeFournisseurs;
import com.iss4u.erp.services.modules.achat.domain.common.models.ListePrix;

import java.util.List;

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
    private GroupeFournisseurs groupeDeFournisseurs;
    private ListePrix listeDePrix;

    private List<FactureAchat> factures;
}