package com.iss4u.erp.services.modules.stock.domain.dto.ecriturestock.response;

import com.iss4u.erp.services.modules.stock.domain.models.LivreInventaire;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.iss4u.erp.services.modules.stock.domain.models.Entrepot;
import com.iss4u.erp.services.modules.achat.domain.common.models.Article;
import com.iss4u.erp.services.modules.stock.domain.models.UniteMesure;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EcritureStockResponse {
    private Long id;
    private String typeEntree;
    private Entrepot entrepotSource;
    private Entrepot entrepotCible;
    private Article articles;
    private Double quantites;
    private UniteMesure unitesMesure;
    private List<LivreInventaire> livreInventaires;


}