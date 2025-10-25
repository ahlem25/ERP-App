package com.iss4u.erp.services.modules.achat.domain.common.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
// import com.iss4u.erp.services.modules.achat.domain.fournisseur.models.Fournisseur;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrixArticle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String codeArticle;

    @Column(nullable = false)
    private String uniteDeMesure;

    private String uniteEmballage;

    private Integer quantiteParUOM;


    private String numeroLot;

    private boolean achat;

    private boolean vente;

    private String devise;

    @Column(nullable = false, precision = 10, scale = 3)
    private BigDecimal taux;

    private LocalDate valableAPartirDu;

    private LocalDate valableJusquau;

    private Integer delaiLivraisonJours;

    @Column(length = 1000)
    private String note;

    @ManyToOne
    @JoinColumn(name = "article_id")
    @JsonBackReference(value = "article-prix-articles")
    private Article article;

    @ManyToOne
    @JsonBackReference(value = "liste-prix-prix-articles")
    private ListePrix listeDePrix;

    // Relation supprimée pour éviter la récursion JSON
    // @ManyToOne
    // @JsonBackReference(value = "fournisseur-prix-articles")
    // private Fournisseur fournisseur;
}

