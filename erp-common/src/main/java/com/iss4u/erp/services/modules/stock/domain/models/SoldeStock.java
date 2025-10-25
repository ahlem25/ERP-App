package com.iss4u.erp.services.modules.stock.domain.models;

import com.iss4u.erp.services.modules.achat.domain.common.models.Article;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class SoldeStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "article_id")
    @JsonBackReference(value = "article-solde-stocks")
    private Article article;
    
    @ManyToOne
    @JoinColumn(name = "entrepot_id")
    @JsonBackReference(value = "entrepot-solde-stock")
    private Entrepot entrepot;
    
    private Double quantiteDisponible;
}
