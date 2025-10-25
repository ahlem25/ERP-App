package com.iss4u.erp.services.modules.stock.domain.dto.vieillissementstock.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import com.iss4u.erp.services.modules.achat.domain.common.models.Article;
import com.iss4u.erp.services.modules.stock.domain.models.Entrepot;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VieillissementStockResponse {
    private Long id;
    private Article article;
    private Entrepot entrepot;
    private Double quantite;
    private String date;
    private Double stock30j;
    private Double stock60j;
    private Double stock90j;
}