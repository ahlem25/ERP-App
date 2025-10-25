package com.iss4u.erp.services.modules.achat.domain.common.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@JsonIgnoreProperties(ignoreUnknown = true)
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
    @JsonBackReference(value = "groupe-article-articles")
    private GroupeArticle groupeArticle;

    @OneToMany(mappedBy = "article")
    @JsonIgnore
    private List<PrixArticle> prixArticles;

    @OneToMany(mappedBy = "article")
    @JsonIgnore
    private List<DemandeMateriel> demandesMateriel;

    @OneToMany(mappedBy = "article")
    @JsonIgnore
    private List<com.iss4u.erp.services.modules.vente.domain.billing.models.CommandeClient> commandes;

    // Relation supprimée car BonLivraison.articlesLivres est de type Article (singulier) et non List<Article>
    // @OneToMany(mappedBy = "articlesLivres")
    // @JsonManagedReference(value = "article-bon-livraisons")
    // private List<BonLivraison> bonLivraisons;

    // Relations avec les entités stock
    @OneToMany(mappedBy = "article")
    @JsonIgnore
    private List<SoldeStock> soldeStocks;

    @OneToMany(mappedBy = "article")
    @JsonIgnore
    private List<EcritureStock> ecritureStocks;

    @OneToMany(mappedBy = "article")
    @JsonIgnore
    private List<LivreInventaire> livreInventaires;

    @OneToMany(mappedBy = "article")
    @JsonIgnore
    private List<VieillissementStock> vieillissementStocks;

    // Collection bonLivraisons supprimée pour éviter les erreurs de cascade
    // La relation existe toujours dans BonLivraison.articlesLivres
}
