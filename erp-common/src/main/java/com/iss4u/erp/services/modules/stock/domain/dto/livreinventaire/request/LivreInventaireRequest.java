package com.iss4u.erp.services.modules.stock.domain.dto.livreinventaire.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LivreInventaireRequest {
    private Long id;
    private Long articleId;
    private Long entrepotId;
    private Long lotId;
    private Long mouvementId;
    private Long numeroSerieId;
    private Double quantite;
    private String date;
}