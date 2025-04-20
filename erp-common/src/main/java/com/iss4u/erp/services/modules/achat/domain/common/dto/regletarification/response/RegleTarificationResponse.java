package com.iss4u.erp.services.modules.achat.domain.common.dto.regletarification.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegleTarificationResponse {
    private Long id;
    private String type;
    private Double conditionQuantite;
    private Double valeur;
    private Integer priorite;


}