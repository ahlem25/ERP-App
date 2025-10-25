package com.iss4u.erp.services.modules.vente.domain.client.dto.groupeclient.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupeClientRequest {
    private String nom;
    private String modeleConditionsPaiementParDefaut;
    private boolean estGroupe;
     private List<Client> clients;

}