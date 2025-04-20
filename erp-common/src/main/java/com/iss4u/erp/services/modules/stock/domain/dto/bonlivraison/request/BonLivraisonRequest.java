package com.iss4u.erp.services.modules.stock.domain.dto.bonlivraison.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.iss4u.erp.services.modules.vente.domain.models.Client;
import com.iss4u.erp.services.modules.achat.domain.common.models.Article;
import com.iss4u.erp.services.modules.vente.domain.models.Taxe;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BonLivraisonRequest {
    private Client client;
    private Article articlesLivres;
    private Taxe taxes;


}