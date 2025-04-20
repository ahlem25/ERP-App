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
public class CommandeClient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numero;
    private Date dateLivraison;
    private String statut;

    @ManyToOne
    private Client client;

    @ManyToMany
    private List<Article> articles;

    @ManyToOne
    private FactureVente facture;

    @ManyToOne
    private Entrepot entrepot;

    @ManyToMany
    private List<Taxe> taxes;
}
