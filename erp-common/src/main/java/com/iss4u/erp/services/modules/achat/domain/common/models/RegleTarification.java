package com.iss4u.erp.services.modules.achat.domain.common.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegleTarification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomSerie;
    private String titre;
    private boolean desactive;
    private String postulerSur;
    private String remiseSur;
    private String entrepot;
    private boolean conditionsMixes;
    private boolean estCumulatif;
    private boolean baseSurCodeCoupon;
    private boolean remiseSurAutreArticle;
    private boolean vente;
    private boolean achat;
    @ManyToOne
    private ListePrix listeDePrix;
    //private Double quantiteMinimale;
   /* private Double quantiteMaximale;
    private Double montantMinimum;
    private Double montantMaximum;
    private String valideAPartirDu;
    private String valideJusquau;
    private String entreprise;
    private String devise;
    private String typeMarge;
    private Double tauxMarge;
    private String typeRemise;
    private Double pourcentageRemise;
    private String listePrix;*/
}
