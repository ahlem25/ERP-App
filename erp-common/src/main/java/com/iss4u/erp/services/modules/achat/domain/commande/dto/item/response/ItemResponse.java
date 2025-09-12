package com.iss4u.erp.services.modules.achat.domain.commande.dto.item.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.iss4u.erp.services.modules.achat.domain.common.models.Article;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemResponse {
    private Long id;
    private Article article;
    private Double quantite;
    private Double prixUnitaire;
    private String commentaire;


}