package com.iss4u.erp.services.modules.achat.domain.commande.models;

import com.iss4u.erp.services.modules.achat.domain.fournisseur.models.Fournisseur;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommandeAchat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serie;
    private String conditionsDeLivraison;

    @ManyToOne
    private Fournisseur fournisseur;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Item> articlesCommandes;

    @OneToMany(cascade = CascadeType.ALL)
    private List<TaxesFrais> taxes;
}


