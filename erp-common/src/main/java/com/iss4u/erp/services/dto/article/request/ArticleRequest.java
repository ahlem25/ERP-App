package com.iss4u.erp.services.dto.article.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleRequest {
    private String codeArticle;
    private String nomArticle;
    private String groupeArticle;
    private String JsonProperty;
    private Boolean maintenirStock;
    private Double prixDeVenteStandard;
}