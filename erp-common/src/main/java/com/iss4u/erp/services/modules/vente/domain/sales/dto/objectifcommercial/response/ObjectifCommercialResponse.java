package com.iss4u.erp.services.modules.vente.domain.sales.dto.objectifcommercial.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.iss4u.erp.services.modules.vente.domain.sales.models.Vendeur;
import com.iss4u.erp.services.modules.achat.domain.common.models.GroupeArticle;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ObjectifCommercialResponse {
    private Long id;
    private String periode;
    private Float montantCible;
    private Vendeur vendeur;
    private GroupeArticle groupeArticle;


}