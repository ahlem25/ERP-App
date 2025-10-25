package com.iss4u.erp.services.modules.achat.domain.commande.models;

import com.iss4u.erp.services.modules.achat.domain.fournisseur.models.Fournisseur;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DevisFournisseur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    //@ManyToOne
    //private Fournisseur fournisseur;

    //@OneToMany(cascade = CascadeType.ALL)
    //private List<Item> articles;

    //@OneToMany(cascade = CascadeType.ALL)
    //private List<TaxesFrais> taxes;
}

