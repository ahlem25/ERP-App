package com.iss4u.erp.services.modules.stock.domain.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.iss4u.erp.services.modules.vente.domain.sales.models.Societe;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @ManyToOne
    private Entrepot entrepotParent;
    private Boolean isGroupWarehouse;
    private Boolean isRejectedWarehouse;


    private String adresse;
    private String telephoneNumber;
    @ManyToOne
    @JsonBackReference(value = "societe-entrepots")
    private Societe societe;
}
