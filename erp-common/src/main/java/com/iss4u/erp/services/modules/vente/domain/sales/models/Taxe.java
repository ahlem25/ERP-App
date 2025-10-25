package com.iss4u.erp.services.modules.vente.domain.sales.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.iss4u.erp.services.modules.vente.domain.billing.models.FactureVente;
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
    
    @ManyToMany(mappedBy = "taxes")
    @JsonBackReference(value = "facture-taxes")
    private List<FactureVente> factures;
}
