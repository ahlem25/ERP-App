package com.iss4u.erp.services.modules.achat.domain.common.dto.article.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BonLivraisonInfo {
    private Long id;
    private String serie;
    private LocalDate date;
    private LocalTime heure;
    private boolean estRetour;
    private String devise;
    private boolean ignorerRegleTarification;
    private BigDecimal quantiteTotale;
    private BigDecimal montantTotal;
    
    // Informations de la taxe (sans référence circulaire)
    private Long taxeId;
    private String taxeNom;
}
