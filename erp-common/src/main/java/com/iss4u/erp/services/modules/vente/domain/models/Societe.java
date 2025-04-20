package com.iss4u.erp.services.modules.vente.domain.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Societe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String adresse;
    private String pays;
    private String deviseParDefaut;
    private String logo;

    @OneToMany(mappedBy = "societe")
    private List<ProfilPDV> profilsPDV;

    @OneToMany(mappedBy = "societe")
    private List<FactureVente> factures;

    @OneToMany(mappedBy = "societe")
    private List<Vendeur> vendeurs;

    @OneToMany(mappedBy = "societe")
    private List<Opportunite> opportunites;
}
