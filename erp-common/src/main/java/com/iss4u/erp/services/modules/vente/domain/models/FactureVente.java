package com.iss4u.erp.services.modules.vente.domain.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private String numero;
    private Date dateEcheance;
    private Float montantTtc;

    @ManyToOne
    private Client client;

    @ManyToOne
    private Societe societe;

    @OneToMany(mappedBy = "facture")
    private List<Paiement> paiements;

    @ManyToMany
    private List<Taxe> taxes;
}
