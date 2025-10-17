package com.iss4u.erp.services.modules.stock.domain.dto.entrepot.response;

import com.iss4u.erp.services.modules.stock.domain.models.LivreInventaire;
import com.iss4u.erp.services.modules.stock.domain.models.SoldeStock;
import com.iss4u.erp.services.modules.vente.domain.billing.models.CommandeClient;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.iss4u.erp.services.modules.stock.domain.models.Entrepot;
import com.iss4u.erp.services.modules.vente.domain.sales.models.Societe;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntrepotResponse {
    private Long id;
    private String nom;
    private Entrepot entrepotParent;
    private Boolean isGroupWarehouse;
    private Boolean isRejectedWarehouse;


    private String adresse;
    private String telephoneNumber;

    private List<SoldeStock> soldeStocks;
    private List<LivreInventaire> livreInventaires;
    private List<CommandeClient> commandes;
}