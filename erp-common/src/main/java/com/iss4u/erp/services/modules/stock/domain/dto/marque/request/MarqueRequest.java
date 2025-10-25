package com.iss4u.erp.services.modules.stock.domain.dto.marque.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.iss4u.erp.services.modules.stock.domain.models.Entrepot;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarqueRequest {
    private String nom;
    private Entrepot entrepotDefaut;
    private Double listePrixDefaut;


}