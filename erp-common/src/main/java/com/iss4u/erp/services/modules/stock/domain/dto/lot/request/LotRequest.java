package com.iss4u.erp.services.modules.stock.domain.dto.lot.request;

import com.iss4u.erp.services.modules.stock.domain.models.LivreInventaire;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.iss4u.erp.services.modules.achat.domain.common.models.Article;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LotRequest {
    private Article article;
    private Date dateProduction;
    private Date dateExpiration;
    private Double garantie;
    private List<LivreInventaire> livreInventaires;


}