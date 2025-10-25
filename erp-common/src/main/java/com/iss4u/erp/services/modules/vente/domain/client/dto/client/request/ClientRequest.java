package com.iss4u.erp.services.modules.vente.domain.client.dto.client.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.iss4u.erp.services.modules.vente.domain.client.models.GroupeClient;
import com.iss4u.erp.services.modules.vente.domain.billing.models.CommandeClient;
import com.iss4u.erp.services.modules.vente.domain.billing.models.Devis;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientRequest {
    private String nom;
    private String adresse;
    private String detailsClient;
    private String devise;
    private Boolean estClientInterne;
    private String siteWeb;
    private String type;
    private String groupeClient;
    
    private GroupeClient groupe;
    private List<CommandeClient> commandes;
    private List<Devis> devis;
}
