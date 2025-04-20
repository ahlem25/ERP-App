package com.iss4u.erp.services.modules.achat.domain.common.dto.prixarticle.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.iss4u.erp.services.modules.achat.domain.common.models.Article;
import com.iss4u.erp.services.modules.achat.domain.common.models.ListePrix;
import com.iss4u.erp.services.modules.achat.domain.fournisseur.models.Fournisseur;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrixArticleRequest {
    private Double prix;
    private Double quantiteMinimale;
    private String devise;
    private String reference;
    private Article article;
    private ListePrix listeDePrix;
    private Fournisseur fournisseur;


}