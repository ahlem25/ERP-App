package com.iss4u.erp.services.modules.vente.domain.client.dto.client.response;

import com.iss4u.erp.services.modules.vente.domain.billing.models.CommandeClient;
import com.iss4u.erp.services.modules.vente.domain.billing.models.Devis;
import com.iss4u.erp.services.modules.vente.domain.billing.models.FactureVente;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.iss4u.erp.services.modules.vente.domain.client.models.GroupeClient;

import java.util.List;

import com.iss4u.erp.services.modules.vente.domain.sales.models.Vendeur;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientResponse {
    private Long id;
    private String nom;
    private String type;
    private String groupeClient;
    private String gestionnaireCompte;
    private String conditionsPaiement;
    private GroupeClient groupe;
    private List<CommandeClient> commandes;
    private List<FactureVente> factures;
    private List<Devis> devis;
    private Vendeur vendeur;


}