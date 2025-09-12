package com.iss4u.erp.services.modules.vente.domain.billing.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.iss4u.erp.services.modules.vente.domain.client.models.Client;
import com.iss4u.erp.services.modules.vente.domain.payment.models.Paiement;
import com.iss4u.erp.services.modules.vente.domain.sales.models.Societe;
import com.iss4u.erp.services.modules.vente.domain.sales.models.Taxe;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FactureVente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serie;

    private LocalDate date;

    private String heurePublication;

    private Boolean modifierDateHeurePublication;

    private LocalDate dateEcheance;

    private Boolean estPaye;

    private Boolean estRetour;

    private Boolean appliquerRetenueImpot;

    private String numeroFactureFournisseur;

    private LocalDate dateFactureFournisseur;

    private String centreDeCouts;

    private String projet;

    private String devise;

    private String numero;

    private Double montantTtc;


    // Dans FactureVente.java
    @ManyToOne
    @JoinColumn(name = "client_id")
    @JsonBackReference
    private Client client;

    @ManyToOne
    @JoinColumn(name = "societe_id")
    @JsonBackReference(value = "societe-factures")
    private Societe societe;

    @OneToMany(mappedBy = "facture")
    @JsonManagedReference(value = "facture-paiement")  // ✅
    private List<Paiement> paiements;



    @ManyToMany
    private List<Taxe> taxes;



}
