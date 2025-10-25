package com.iss4u.erp.services.modules.stock.domain.models;

import com.iss4u.erp.services.modules.achat.domain.common.models.Article;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VieillissementStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "article_id")
    @JsonBackReference(value = "article-vieillissement-stocks")
    private Article article;
    
    @ManyToOne
    @JoinColumn(name = "entrepot_id")
    @JsonBackReference(value = "entrepot-vieillissement-stock")
    private Entrepot entrepot;
    
    private Double quantite;
    private String date;
    private Double stock30j;
    private Double stock60j;
    private Double stock90j;
}
