package com.iss4u.erp.services.modules.vente.domain.billing.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.iss4u.erp.services.modules.achat.domain.common.models.Article;
import com.iss4u.erp.services.modules.achat.domain.common.models.ListePrix;
import com.iss4u.erp.services.modules.stock.domain.models.Entrepot;
import com.iss4u.erp.services.modules.vente.domain.client.models.Client;
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
public class CommandeClient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serie;
    private LocalDate date;
    private String bonCommandeClient;
    private String typeCommande;
    private LocalDate dateLivraison;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @JsonBackReference(value = "client-commandes")
    private Client client;

    @ManyToOne
    private Article article;

    @ManyToOne
    private FactureVente facture;

    @ManyToOne
    private Entrepot entrepot;

    @ManyToOne
    @JoinColumn(name = "taxe_id", nullable = true)
    @JsonBackReference
    private Taxe taxe;

    @ManyToOne
    private ListePrix listePrix;
}
