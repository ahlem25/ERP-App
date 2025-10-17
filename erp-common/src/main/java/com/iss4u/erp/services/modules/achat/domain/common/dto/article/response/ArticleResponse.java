package com.iss4u.erp.services.modules.achat.domain.common.dto.article.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iss4u.erp.services.modules.achat.domain.commande.models.DemandeMateriel;
import com.iss4u.erp.services.modules.achat.domain.common.models.GroupeArticle;
import com.iss4u.erp.services.modules.achat.domain.common.models.PrixArticle;
import com.iss4u.erp.services.modules.stock.domain.models.BonLivraison;
import com.iss4u.erp.services.modules.stock.domain.models.LivreInventaire;
import com.iss4u.erp.services.modules.stock.domain.models.SoldeStock;
import com.iss4u.erp.services.modules.vente.domain.billing.models.CommandeClient;
import com.iss4u.erp.services.modules.vente.domain.client.models.Client;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleResponse {
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
    private List<PrixArticle> prixArticle;

    @OneToMany(mappedBy = "article")
    private List<DemandeMateriel> demandesMateriel;

    private List<BonLivraison> bonLivraisons;
    private Client client;
    private List<SoldeStock> soldeStocks;
    private List<LivreInventaire> livreInventaires;
    private List<CommandeClient> commandes;
}