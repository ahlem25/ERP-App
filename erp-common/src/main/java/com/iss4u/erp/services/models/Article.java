package com.iss4u.erp.services.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

;
;

@Entity
@Table(name = "articles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code_article;
    private String nom_article;
    private String groupe_article;
    private String unite_de_mesure;
    private Boolean maintenir_stock;
    private Double prix_de_vente_standard;
}
