package com.iss4u.erp.services.modules.stock.domain.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.iss4u.erp.services.modules.achat.domain.common.models.Article;
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
    @JoinColumn(name = "article_id")  // Clé étrangère vers Article
    @JsonBackReference(value = "article-soldeStock")
    private Article article;

    @ManyToOne
    @JoinColumn(name = "entrepot_id")  // Clé étrangère vers Entrepot
    @JsonBackReference(value = "entrepot-soldeStock")
    private Entrepot entrepot;

    private Double quantiteDisponible;
}
