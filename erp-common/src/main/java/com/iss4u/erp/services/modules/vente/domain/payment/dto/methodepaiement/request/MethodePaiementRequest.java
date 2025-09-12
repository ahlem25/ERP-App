package com.iss4u.erp.services.modules.vente.domain.payment.dto.methodepaiement.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MethodePaiementRequest {
    private String nom;
    private String type;


}