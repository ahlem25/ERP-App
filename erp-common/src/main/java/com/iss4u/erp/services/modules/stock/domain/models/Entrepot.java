package com.iss4u.erp.services.modules.stock.domain.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
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
private String nom;
    @ManyToOne private Entrepot entrepotParent;
    private Boolean isGroupWarehouse;
    private Boolean isRejectedWarehouse;
    @ManyToOne private Societe societe;
}
