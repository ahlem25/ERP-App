package com.iss4u.erp.services.modules.achat.domain.common.dto.article.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArticleRequest {
    private String codeArticle;
    private String nomArticle;
    private Boolean stockable;
    private String unite;
    private String image;
    private Double prixVente;
    
    // IDs des entités liées (pas les objets entiers)
    private Long groupeArticleId;
}