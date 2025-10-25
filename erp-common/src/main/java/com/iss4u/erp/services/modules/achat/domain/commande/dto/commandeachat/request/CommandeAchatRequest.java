package com.iss4u.erp.services.modules.achat.domain.commande.dto.commandeachat.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommandeAchatRequest {
    private String serie;
    private String conditionsDeLivraison;
    private Fournisseur fournisseur;
    private List<Item> articlesCommandes;
    private List<TaxesFrais> taxes;


}