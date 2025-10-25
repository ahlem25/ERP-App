package com.iss4u.erp.services.modules.achat.domain.common.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iss4u.erp.services.modules.achat.domain.commande.models.DemandeMateriel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private Boolean stockable;
    private String unite;
    private String image;
    private Double prixVente;

    @ManyToOne
    @JoinColumn(name = "groupe_article_id")
    private GroupeArticle groupeArticle;

    @OneToMany(mappedBy = "article")
    private List<PrixArticle> prixArticles;

    @OneToMany(mappedBy = "article")
    @JsonIgnoreProperties("article")
    private List<DemandeMateriel> demandesMateriel;
} 