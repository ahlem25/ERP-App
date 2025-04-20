package com.iss4u.erp.services.modules.achat.domain.commande.dto.item.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.iss4u.erp.services.modules.achat.domain.common.models.Article;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequest {
    private Article article;
    private Double quantite;
    private Double prixUnitaire;
    private String commentaire;


}