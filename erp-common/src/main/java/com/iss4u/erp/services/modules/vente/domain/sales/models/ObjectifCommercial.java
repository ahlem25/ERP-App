package com.iss4u.erp.services.modules.vente.domain.sales.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.iss4u.erp.services.modules.achat.domain.common.models.GroupeArticle;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ObjectifCommercial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String periode;
    private Float montantCible;

    @ManyToOne
    @JoinColumn(name = "vendeur_id")
    @JsonBackReference(value = "vendeur-objectifs")
    private Vendeur vendeur;

    @ManyToOne
    @JoinColumn(name = "groupe_article_id")
    @JsonBackReference(value = "groupe-article-objectifs")
    private GroupeArticle groupeArticle;
}
