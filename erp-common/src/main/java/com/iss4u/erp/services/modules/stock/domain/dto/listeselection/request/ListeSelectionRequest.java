package com.iss4u.erp.services.modules.stock.domain.dto.listeselection.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.iss4u.erp.services.modules.achat.domain.common.models.Article;
import com.iss4u.erp.services.modules.stock.domain.models.Entrepot;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListeSelectionRequest {
    private Article articlesAPrelever;
    private Entrepot entrepotSource;
    private Double quantitesPrelevees;


}