package com.iss4u.erp.services.modules.stock.domain.dto.vieillissementstock.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.iss4u.erp.services.modules.achat.domain.common.models.Article;
import com.iss4u.erp.services.modules.stock.domain.models.Entrepot;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VieillissementStockRequest {
    private Article article;
    private Entrepot entrepot;
    private Double stock30j;
    private Double stock60j;
    private Double stock90j;


}