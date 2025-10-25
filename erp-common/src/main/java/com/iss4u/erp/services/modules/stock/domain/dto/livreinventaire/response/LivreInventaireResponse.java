package com.iss4u.erp.services.modules.stock.domain.dto.livreinventaire.response;

import com.iss4u.erp.services.modules.achat.domain.common.models.Article;
import com.iss4u.erp.services.modules.stock.domain.models.Entrepot;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LivreInventaireResponse {
    private Long id;
    private Long articleId;
    private String articleNom;
    private Article article;
    private Long entrepotId;
    private String entrepotNom;
    private Entrepot entrepot;
    private Long lotId;
    private Long mouvementId;
    private Long numeroSerieId;
    private Double quantite;
    private String date;
}