package com.iss4u.erp.services.modules.stock.domain.dto.numeroserie.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.iss4u.erp.services.modules.achat.domain.common.models.Article;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NumeroSerieResponse {
    private Long id;
    private String idSerie;
    private Article article;
    private Double garantie;


}