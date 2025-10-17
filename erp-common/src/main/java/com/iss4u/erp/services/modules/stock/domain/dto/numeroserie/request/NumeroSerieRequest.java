package com.iss4u.erp.services.modules.stock.domain.dto.numeroserie.request;

import com.iss4u.erp.services.modules.stock.domain.models.LivreInventaire;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.iss4u.erp.services.modules.achat.domain.common.models.Article;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NumeroSerieRequest {
    private String idSerie;
    private Article article;
    private Double garantie;

    private List<LivreInventaire> livreInventaires;

}