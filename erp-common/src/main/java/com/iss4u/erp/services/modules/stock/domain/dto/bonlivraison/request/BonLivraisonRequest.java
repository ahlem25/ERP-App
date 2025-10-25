package com.iss4u.erp.services.modules.stock.domain.dto.bonlivraison.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BonLivraisonRequest {
    private String serie;
    private LocalDate date;
    private LocalTime heure;
    private boolean estRetour;
    private String devise;
    private boolean ignorerRegleTarification;
    private BigDecimal quantiteTotale;
    private BigDecimal montantTotal;
    
    // IDs des entités liées (pas les objets entiers)
    private Long articleId;
    private Long taxeId;
}