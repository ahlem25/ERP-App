package com.iss4u.erp.services.modules.achat.domain.common.dto.prixarticle.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrixArticleRequest {
    private Long id;
    private String codeArticle;
    private String uniteDeMesure;
    private String uniteEmballage;
    private Integer quantiteParUOM;
    private String numeroLot;
    private boolean achat;
    private boolean vente;
    private String devise;
    private BigDecimal taux;
    private LocalDate valableAPartirDu;
    private LocalDate valableJusquau;
    private Integer delaiLivraisonJours;
    private String note;
    
    // Relations avec IDs seulement
    private Long articleId;
    private Long listeDePrixId;
    private Long fournisseurId;
}