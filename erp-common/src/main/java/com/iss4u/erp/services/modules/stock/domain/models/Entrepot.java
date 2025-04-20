package com.iss4u.erp.services.modules.stock.domain.models;

import com.iss4u.erp.services.modules.vente.domain.models.Societe;
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
    @ManyToOne
    private Societe societe;
}
