package com.iss4u.erp.services.modules.achat.domain.commande.dto.devisfournisseur.request;

import com.iss4u.erp.services.modules.achat.domain.commande.models.Item;
import com.iss4u.erp.services.modules.achat.domain.commande.models.TaxesFrais;
import com.iss4u.erp.services.modules.achat.domain.common.models.Article;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.iss4u.erp.services.modules.achat.domain.fournisseur.models.Fournisseur;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DevisFournisseurRequest {
    private String serie;
    private String statut;
    private LocalDate date;
    private LocalDate caisseValable;
    private String numeroDevis;
    private String centreDeCouts;
    private String projet;
    private String modeleTaxesFrais;
    private BigDecimal totalTaxesAjoutees;
    private BigDecimal totalTaxesDeduites;
    private BigDecimal totalTaxesEtFrais;
    private BigDecimal totalGeneral;
    private BigDecimal ajustementArrondi;
    private BigDecimal totalArrondi;
    private Boolean desactiverTotalArrondi;
    private Fournisseur fournisseur;



}