package com.iss4u.erp.services.modules.stock.domain.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Article {
private String code;
    private String nom;
    @ManyToOne private GroupeArticle groupeArticle;
    private Double prix;
    @ManyToOne private Taxes taxes;
    private Boolean maintenirStock;
    private Boolean aVariantes;
    private Boolean estImmobilisation;
}
