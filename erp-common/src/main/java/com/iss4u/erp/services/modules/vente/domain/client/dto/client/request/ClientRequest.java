package com.iss4u.erp.services.modules.vente.domain.client.dto.client.request;

import com.iss4u.erp.services.modules.achat.domain.common.models.ListePrix;
import com.iss4u.erp.services.modules.core.domain.models.Utilisateur;
import com.iss4u.erp.services.modules.stock.domain.models.BonLivraison;
import com.iss4u.erp.services.modules.vente.domain.client.models.GroupeClient;
import com.iss4u.erp.services.modules.vente.domain.billing.models.CommandeClient;
import com.iss4u.erp.services.modules.vente.domain.billing.models.Devis;
import com.iss4u.erp.services.modules.vente.domain.billing.models.FactureVente;
import com.iss4u.erp.services.modules.vente.domain.payment.models.MethodePaiement;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;
import java.util.List;

import com.iss4u.erp.services.modules.vente.domain.sales.models.Vendeur;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientRequest {
    private String nom;
    private String type;
    private String devise;
    private String adresse;
    private String siteWeb;
    private Boolean estClientInterne;
    @Column(length = 2000)
    private String detailsClient;
    private GroupeClient groupe;
    private List<CommandeClient> commandes;
    private List<FactureVente> factures;
    private List<Devis> devis;
    private ListePrix listePrix;

    private List<BonLivraison> bonLivraisons;
}