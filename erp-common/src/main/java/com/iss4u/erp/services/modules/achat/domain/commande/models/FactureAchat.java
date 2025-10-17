package com.iss4u.erp.services.modules.achat.domain.commande.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.iss4u.erp.services.modules.achat.domain.fournisseur.models.Fournisseur;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FactureAchat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @ManyToOne
    @JoinColumn(name = "fournisseur_id", nullable = false)
    @JsonBackReference(value = "factures-fournisseur")
    private Fournisseur fournisseur;



}
