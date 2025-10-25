package com.iss4u.erp.services.modules.achat.domain.commande.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iss4u.erp.services.modules.achat.domain.common.models.Article;
import com.iss4u.erp.services.modules.stock.domain.models.Entrepot;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DemandeMateriel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serie;

    private String type;

    private LocalDate dateTransaction;

    private String requisPar;

    private String commentaire;

    @ManyToOne
    @JoinColumn(name = "entrepot_cible_id")
    @JsonBackReference(value = "entrepot-demandes-materiel")
    private Entrepot entrepotCible;

    @ManyToOne
    @JoinColumn(name = "article_id")
    @JsonBackReference(value = "article-demandes-materiel")
    private Article article;
}