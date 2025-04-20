package com.iss4u.erp.services.modules.achat.domain.commande.dto.commandeachat.response;

import com.iss4u.erp.services.modules.achat.domain.commande.models.Item;
import com.iss4u.erp.services.modules.achat.domain.commande.models.TaxesFrais;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.iss4u.erp.services.modules.achat.domain.fournisseur.models.Fournisseur;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommandeAchatResponse {
    private Long id;
    private String serie;
    private String conditionsDeLivraison;
    private Fournisseur fournisseur;
    private List<Item> articlesCommandes;
    private List<TaxesFrais> taxes;


}