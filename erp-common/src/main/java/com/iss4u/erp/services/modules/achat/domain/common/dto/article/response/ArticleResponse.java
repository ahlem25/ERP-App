package com.iss4u.erp.services.modules.achat.domain.common.dto.article.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleResponse {
    private Long id;
    private String codeArticle;
    private String nomArticle;
    private Boolean stockable;
    private String unite;
    private String image;
    private Double prixVente;
    
    // IDs des entités liées pour éviter les références circulaires
    private Long groupeArticleId;
    private String groupeArticleNom;
    
    // IDs des collections liées
    private List<Long> prixArticleIds;
    private List<Long> demandeMaterielIds;
    private List<Long> commandeClientIds;
    private List<Long> bonLivraisonIds;
    private List<Long> soldeStockIds;
    private List<Long> ecritureStockIds;
    private List<Long> livreInventaireIds;
    private List<Long> vieillissementStockIds;
    
    // Informations des bons de livraison liés (sans référence circulaire)
    // Cette liste sera remplie par le service ArticleBonLivraisonService
    private List<BonLivraisonInfo> bonLivraisons;
}