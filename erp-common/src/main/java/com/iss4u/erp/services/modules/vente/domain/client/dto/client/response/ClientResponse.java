package com.iss4u.erp.services.modules.vente.domain.client.dto.client.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientResponse {
    private Long id;
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