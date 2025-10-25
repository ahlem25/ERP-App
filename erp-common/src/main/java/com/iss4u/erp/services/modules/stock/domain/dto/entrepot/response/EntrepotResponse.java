package com.iss4u.erp.services.modules.stock.domain.dto.entrepot.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.iss4u.erp.services.modules.stock.domain.models.Entrepot;
import com.iss4u.erp.services.modules.vente.domain.sales.models.Societe;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntrepotResponse {
    private Long id;
    private String nom;
    private String adresse;
    private String telephoneNumber;
    private Entrepot entrepotParent;
    private Boolean isGroupWarehouse;
    private Boolean isRejectedWarehouse;
    private Societe societe;


}