package com.iss4u.erp.services.modules.stock.domain.dto.ecriturestock.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.iss4u.erp.services.modules.stock.domain.models.Entrepot;
import com.iss4u.erp.services.modules.achat.domain.common.models.Article;
import com.iss4u.erp.services.modules.stock.domain.models.UniteMesure;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EcritureStockRequest {
    private String typeEntree;
    private Entrepot entrepotSource;
    private Entrepot entrepotCible;
    private Article articles;
    private Double quantites;
    private UniteMesure unitesMesure;


}