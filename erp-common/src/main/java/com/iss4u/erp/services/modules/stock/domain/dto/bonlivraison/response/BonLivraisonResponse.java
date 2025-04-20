package com.iss4u.erp.services.modules.stock.domain.dto.bonlivraison.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.iss4u.erp.services.modules.vente.domain.client.models.Client;
import com.iss4u.erp.services.modules.achat.domain.common.models.Article;
import com.iss4u.erp.services.modules.vente.domain.sales.models.Taxe;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BonLivraisonResponse {
    private Long id;
    private Client client;
    private Article articlesLivres;
    private Taxe taxes;


}