package com.iss4u.erp.services.modules.achat.domain.common.dto.article.request;

import com.iss4u.erp.services.modules.achat.domain.commande.models.DemandeMateriel;
import com.iss4u.erp.services.modules.achat.domain.common.models.GroupeArticle;
import com.iss4u.erp.services.modules.achat.domain.common.models.PrixArticle;
import com.iss4u.erp.services.modules.stock.domain.models.BonLivraison;
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
public class ArticleRequest {
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
    private List<DemandeMateriel> demandesMateriel;

    private List<BonLivraison> bonLivraisons;
}