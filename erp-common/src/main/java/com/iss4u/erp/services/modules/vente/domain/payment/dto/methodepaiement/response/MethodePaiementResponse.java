package com.iss4u.erp.services.modules.vente.domain.payment.dto.methodepaiement.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MethodePaiementResponse {
    private Long id;
    private String nom;
    private String type;


}