package com.iss4u.erp.services.modules.vente.domain.billing.dto.commandeclient.response;

import com.iss4u.erp.services.modules.achat.domain.common.models.Article;
import com.iss4u.erp.services.modules.achat.domain.common.models.ListePrix;
import com.iss4u.erp.services.modules.vente.domain.sales.models.Taxe;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

import com.iss4u.erp.services.modules.vente.domain.client.models.Client;

import java.util.List;

import com.iss4u.erp.services.modules.vente.domain.billing.models.FactureVente;
import com.iss4u.erp.services.modules.stock.domain.models.Entrepot;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommandeClientResponse {
    private Long id;
    private String serie;
    private LocalDate date;
    private String bonCommandeClient;
    private String typeCommande;
    private LocalDate dateLivraison;
    private Client client;
    private Article article;
    private FactureVente facture;
    private Entrepot entrepot;
    private Taxe taxe;


}