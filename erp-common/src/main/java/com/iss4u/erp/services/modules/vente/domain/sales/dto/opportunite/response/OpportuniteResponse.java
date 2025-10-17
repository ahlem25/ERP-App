package com.iss4u.erp.services.modules.vente.domain.sales.dto.opportunite.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.iss4u.erp.services.modules.vente.domain.client.models.Client;
import com.iss4u.erp.services.modules.vente.domain.sales.models.Societe;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpportuniteResponse {
    private Long id;
    private String nom;
    private Float probabilite;
    private Float montantEstime;
    private Client clientPotentiel;



}