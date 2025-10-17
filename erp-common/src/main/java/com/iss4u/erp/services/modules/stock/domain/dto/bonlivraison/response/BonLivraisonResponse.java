package com.iss4u.erp.services.modules.stock.domain.dto.bonlivraison.response;

import com.iss4u.erp.services.modules.achat.domain.common.models.ListePrix;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.iss4u.erp.services.modules.vente.domain.client.models.Client;
import com.iss4u.erp.services.modules.achat.domain.common.models.Article;
import com.iss4u.erp.services.modules.vente.domain.sales.models.Taxe;

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
    private Article article;
    private Taxe taxe;


}