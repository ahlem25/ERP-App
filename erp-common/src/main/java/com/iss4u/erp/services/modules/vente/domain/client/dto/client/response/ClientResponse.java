package com.iss4u.erp.services.modules.vente.domain.client.dto.client.response;

import com.iss4u.erp.services.modules.vente.domain.models.CommandeClient;
import com.iss4u.erp.services.modules.vente.domain.models.Devis;
import com.iss4u.erp.services.modules.vente.domain.models.FactureVente;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.iss4u.erp.services.modules.vente.domain.client.models.GroupeClient;

import java.util.List;

import com.iss4u.erp.services.modules.vente.domain.models.Vendeur;

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