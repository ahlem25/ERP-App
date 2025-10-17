package com.iss4u.erp.services.modules.achat.domain.common.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.iss4u.erp.services.modules.achat.domain.commande.models.DemandeMateriel;
import com.iss4u.erp.services.modules.stock.domain.models.BonLivraison;
import com.iss4u.erp.services.modules.stock.domain.models.LivreInventaire;
import com.iss4u.erp.services.modules.stock.domain.models.SoldeStock;
import com.iss4u.erp.services.modules.vente.domain.billing.models.CommandeClient;
import com.iss4u.erp.services.modules.vente.domain.client.models.Client;
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
    private List<PrixArticle> prixArticle;

    @OneToMany(mappedBy = "article")
    @JsonManagedReference(value = "article-demande")
    private List<DemandeMateriel> demandesMateriel;


    @ManyToOne
    @JoinColumn(name = "client_id")
    @JsonBackReference(value = "client-articles")
    private Client client;

    @OneToMany(mappedBy = "article")
    @JsonManagedReference(value = "article-soldeStock")
    private List<SoldeStock> soldeStocks;

    @OneToMany(mappedBy = "article")
    @JsonManagedReference(value = "article-commandes")
    private List<CommandeClient> commandes;



    @OneToMany(mappedBy = "article")
    @JsonManagedReference(value = "article-livreInventaire")
    private List<LivreInventaire> livreInventaires;



}
