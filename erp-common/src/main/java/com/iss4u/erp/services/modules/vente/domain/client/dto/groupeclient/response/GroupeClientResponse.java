package com.iss4u.erp.services.modules.vente.domain.client.dto.groupeclient.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupeClientResponse {
    private Long id;
    private String nom;
    private String modeleConditionsPaiementParDefaut;
    private boolean estGroupe;
     private List<Client> clients;

}