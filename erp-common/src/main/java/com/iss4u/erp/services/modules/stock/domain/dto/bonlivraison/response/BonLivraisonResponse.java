package com.iss4u.erp.services.modules.stock.domain.dto.bonlivraison.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BonLivraisonResponse {
    private Long id;
    private String serie;
    private LocalDate date;
    private LocalTime heure;
    private boolean estRetour;
    private String devise;
    private boolean ignorerRegleTarification;
    private BigDecimal quantiteTotale;
    private BigDecimal montantTotal;
    
    // Informations de l'article (sans référence circulaire)
    private Long articleId;
    private String articleCode;
    private String articleNom;
    private Double articlePrixVente;
    private String articleUnite;
    
    // Informations de la taxe (sans référence circulaire)
    private Long taxeId;
    private String taxeNom;
}