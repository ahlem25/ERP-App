package com.iss4u.erp.services.modules.stock.domain.dto.lot.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.iss4u.erp.services.modules.achat.domain.common.models.Article;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LotResponse {
    private Long id;
    private Article article;
    private Date dateProduction;
    private Date dateExpiration;
    private Double garantie;


}