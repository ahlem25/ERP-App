package com.iss4u.erp.services.modules.achat.domain.common.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.iss4u.erp.services.modules.achat.domain.commande.models.DemandeMateriel;
import com.iss4u.erp.services.modules.stock.domain.models.BonLivraison;
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
    @JsonBackReference(value = "article-groupe-article")
    private GroupeArticle groupeArticle;

    @OneToMany(mappedBy = "article")
    @JsonManagedReference(value = "article-prix")
    private List<PrixArticle> prixArticles;

    @OneToMany(mappedBy = "article")
    @JsonManagedReference(value = "article-demande")
    private List<DemandeMateriel> demandesMateriel;


    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "article-bonlivraison")
    private List<BonLivraison> bonLivraisons;

}
