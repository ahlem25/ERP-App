package com.iss4u.erp.services.modules.vente.domain.sales.dto.vendeur.response;

import com.iss4u.erp.services.modules.vente.domain.client.models.Client;
import com.iss4u.erp.services.modules.vente.domain.sales.models.ObjectifCommercial;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.iss4u.erp.services.modules.vente.domain.sales.models.Societe;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendeurResponse {
    private Long id;
    private String nom;
    private Float commission;
    private String equipeParente;
    private Societe societe;
    private List<ObjectifCommercial> objectifs;
    private List<Client> clients;


}