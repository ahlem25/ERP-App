package com.iss4u.erp.services.modules.stock.domain.dto.receptionachat.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.iss4u.erp.services.modules.achat.domain.fournisseur.models.Fournisseur;
import com.iss4u.erp.services.modules.achat.domain.common.models.Article;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceptionAchatResponse {
    private Long id;
    private Fournisseur fournisseur;
    private Article articlesRecus;
    private Double quantitesAcceptees;
    private Double quantitesRejetees;


}