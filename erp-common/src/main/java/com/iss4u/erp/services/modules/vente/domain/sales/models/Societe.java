package com.iss4u.erp.services.modules.vente.domain.sales.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.iss4u.erp.services.modules.vente.domain.billing.models.FactureVente;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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
    private String telephoneNumber;
    private String pays;
    private String deviseParDefaut;
    @Column(name = "numero_identification_fiscale")
    private String numeroIdentificationFiscale;

    private String domaine;

    @Column(name = "date_creation")
    private LocalDate dateCreation;

    @Column(name = "est_groupe")
    private Boolean estGroupe = false;

    @OneToMany(mappedBy = "societe")
    @JsonIgnore
    private List<ProfilPDV> profilsPDV;

    @OneToMany(mappedBy = "societe")
    @JsonIgnore
    private List<FactureVente> factures;

    @OneToMany(mappedBy = "societe")
    @JsonIgnore
    private List<Vendeur> vendeurs;

    @OneToMany(mappedBy = "societe")
    @JsonIgnore
    private List<Opportunite> opportunites;
}
