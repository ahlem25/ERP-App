package com.iss4u.erp.services.modules.stock.domain.models;

import com.iss4u.erp.services.modules.vente.domain.sales.models.Societe;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Entrepot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String adresse;
    private String telephoneNumber;
    @ManyToOne
    @JoinColumn(name = "entrepot_parent_id")
    @JsonBackReference(value = "entrepot-parent")
    private Entrepot entrepotParent;
    private Boolean isGroupWarehouse;
    private Boolean isRejectedWarehouse;
    @ManyToOne
    @JoinColumn(name = "societe_id")
    @JsonBackReference(value = "societe-entrepots")
    private Societe societe;

    // Relations avec les entités stock
    @OneToMany(mappedBy = "entrepot")
    @JsonManagedReference(value = "entrepot-solde-stock")
    private List<SoldeStock> soldeStocks;

    @OneToMany(mappedBy = "entrepotSource")
    @JsonManagedReference(value = "entrepot-ecriture-stock-source")
    private List<EcritureStock> ecritureStocksSource;

    @OneToMany(mappedBy = "entrepotCible")
    @JsonManagedReference(value = "entrepot-ecriture-stock-cible")
    private List<EcritureStock> ecritureStocksCible;

    @OneToMany(mappedBy = "entrepot")
    @JsonManagedReference(value = "entrepot-livre-inventaire")
    private List<LivreInventaire> livreInventaires;

    @OneToMany(mappedBy = "entrepot")
    @JsonManagedReference(value = "entrepot-vieillissement-stock")
    private List<VieillissementStock> vieillissementStocks;
}
