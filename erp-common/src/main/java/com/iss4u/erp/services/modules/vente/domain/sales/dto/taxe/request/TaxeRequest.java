package com.iss4u.erp.services.modules.vente.domain.sales.dto.taxe.request;

import com.iss4u.erp.services.modules.stock.domain.models.BonLivraison;
import com.iss4u.erp.services.modules.vente.domain.billing.models.CommandeClient;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaxeRequest {
    private String nom;
    private Float taux;
    private String type;
    private List<BonLivraison> bonLivraisons;
    private List<CommandeClient> commandes;
}