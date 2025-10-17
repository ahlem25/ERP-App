package com.iss4u.erp.services.modules.achat.domain.common.dto.prixarticle.response;

import com.iss4u.erp.services.modules.achat.domain.common.models.ListePrix;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.iss4u.erp.services.modules.achat.domain.common.models.Article;

import com.iss4u.erp.services.modules.achat.domain.fournisseur.models.Fournisseur;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrixArticleResponse {
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
    private Article article;
    private ListePrix listePrix;




}