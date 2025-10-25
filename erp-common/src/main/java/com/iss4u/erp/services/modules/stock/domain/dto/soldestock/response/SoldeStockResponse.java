package com.iss4u.erp.services.modules.stock.domain.dto.soldestock.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.iss4u.erp.services.modules.achat.domain.common.models.Article;
import com.iss4u.erp.services.modules.stock.domain.models.Entrepot;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SoldeStockResponse {
    private Long id;
    private Article article;
    private Entrepot entrepot;
    private Double quantiteDisponible;


}