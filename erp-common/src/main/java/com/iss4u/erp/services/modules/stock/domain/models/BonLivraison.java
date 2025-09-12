package com.iss4u.erp.services.modules.stock.domain.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.iss4u.erp.services.modules.achat.domain.common.models.Article;
import com.iss4u.erp.services.modules.achat.domain.common.models.ListePrix;
import com.iss4u.erp.services.modules.vente.domain.client.models.Client;
import com.iss4u.erp.services.modules.vente.domain.sales.models.Taxe;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BonLivraison {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serie;
    private LocalDate date;
    private LocalTime heure;
    private Boolean estRetour;


    private String devise;

    private Boolean ignorerRegleTarification;

    private Double quantiteTotale;
    private Double montantTotal;

    @ManyToOne
    @JoinColumn(name = "liste_prix_id")
    @JsonBackReference(value = "listePrix-bonlivraisons")
    private ListePrix listePrix;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @JsonBackReference(value = "client-bonlivraison")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "article_id")
    @JsonBackReference(value = "article-bonlivraison")
    private Article article;


    @ManyToOne
    @JoinColumn(name = "taxe_id")
    @JsonBackReference(value = "taxe-bonlivraison")
    private Taxe taxe;

}
