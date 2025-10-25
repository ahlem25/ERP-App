package com.iss4u.erp.services.modules.achat.domain.commande.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaxesFrais {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String typeDeTaxe;
    private String compteComptable;
    private Double taux;
    private Double montant;
    
    @ManyToOne
    @JoinColumn(name = "commande_achat_id")
    @JsonBackReference(value = "commande-achat-taxes")
    private CommandeAchat commandeAchat;
}

