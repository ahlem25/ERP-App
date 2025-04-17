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
    private String codeArticle;
    private String nomArticle;
    private String groupeArticle;
    private String JsonProperty;
    private Boolean maintenirStock;
    private Double prixDeVenteStandard;
}
