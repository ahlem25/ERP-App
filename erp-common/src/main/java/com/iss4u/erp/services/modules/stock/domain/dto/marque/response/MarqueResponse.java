package com.iss4u.erp.services.modules.stock.domain.dto.marque.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.iss4u.erp.services.modules.stock.domain.models.Entrepot;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarqueResponse {
    private Long id;
    private String nom;
    private Entrepot entrepotDefaut;
    private Double listePrixDefaut;


}