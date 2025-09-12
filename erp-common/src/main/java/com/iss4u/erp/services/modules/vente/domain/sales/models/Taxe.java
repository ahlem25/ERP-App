package com.iss4u.erp.services.modules.vente.domain.sales.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.iss4u.erp.services.modules.stock.domain.models.BonLivraison;
import com.iss4u.erp.services.modules.vente.domain.billing.models.CommandeClient;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Taxe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private Float taux;
    private String type;


    @OneToMany(mappedBy = "taxe", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "taxe-bonlivraison")
    private List<BonLivraison> bonLivraisons;

    @OneToMany(mappedBy = "taxe", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CommandeClient> commandes;
}
