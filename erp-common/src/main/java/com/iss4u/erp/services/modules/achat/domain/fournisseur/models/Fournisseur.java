package com.iss4u.erp.services.modules.achat.domain.fournisseur.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.iss4u.erp.services.modules.achat.domain.commande.models.FactureAchat;
import com.iss4u.erp.services.modules.achat.domain.common.models.ListePrix;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    @ManyToOne
    @JsonBackReference(value = "groupeFournisseurs-fournisseurs")
    private GroupeFournisseurs groupeDeFournisseurs;

    @ManyToOne
    @JsonBackReference(value = "listePrix-fournisseurs")
    private ListePrix listeDePrix;

    @OneToMany(mappedBy = "fournisseur", cascade = CascadeType.ALL, orphanRemoval = false)
    @JsonManagedReference(value = "factures-fournisseur")
    private List<FactureAchat> factures;

}
