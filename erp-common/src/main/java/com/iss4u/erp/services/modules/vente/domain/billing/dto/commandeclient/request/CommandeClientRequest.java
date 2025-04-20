package com.iss4u.erp.services.modules.vente.domain.billing.dto.commandeclient.request;

import com.iss4u.erp.services.modules.achat.domain.common.models.Article;
import com.iss4u.erp.services.modules.vente.domain.sales.models.Taxe;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;

import com.iss4u.erp.services.modules.vente.domain.client.models.Client;

import java.util.List;

import com.iss4u.erp.services.modules.vente.domain.billing.models.FactureVente;
import com.iss4u.erp.services.modules.stock.domain.models.Entrepot;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommandeClientRequest {
    private String numero;
    private Date dateLivraison;
    private String statut;
    private Client client;
    private List<Article> articles;
    private FactureVente facture;
    private Entrepot entrepot;
    private List<Taxe> taxes;


}