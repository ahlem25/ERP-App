package com.iss4u.erp.services.dto.article.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleResponse {
    private Long id;
    private String code_article;
    private String nom_article;
    private String groupe_article;
    private String unite_de_mesure;
    private Boolean maintenir_stock;
    private Double prix_de_vente_standard;


}