package com.iss4u.erp.services.modules.achat.domain.fournisseur.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.iss4u.erp.services.modules.achat.domain.common.models.ListePrix;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Fournisseur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "groupe_fournisseurs_id")
    @JsonBackReference(value = "groupe-fournisseurs-fournisseurs")
    private GroupeFournisseurs groupeDeFournisseurs;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "liste_prix_id")
    @JsonBackReference(value = "liste-prix-fournisseurs")
    private ListePrix listeDePrix;

    @OneToMany(mappedBy = "fournisseur", fetch = FetchType.LAZY)
    @JsonManagedReference(value = "fournisseur-factures-achat")
    private java.util.List<com.iss4u.erp.services.modules.achat.domain.commande.models.FactureAchat> facturesAchat;
}
