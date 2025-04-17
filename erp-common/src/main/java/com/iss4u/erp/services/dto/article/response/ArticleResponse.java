package com.iss4u.erp.services.dto.article.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleResponse {
    private Long id;
    private String codeArticle;
    private String nomArticle;
    private String groupeArticle;
    private String JsonProperty;
    private Boolean maintenirStock;
    private Double prixDeVenteStandard;
}