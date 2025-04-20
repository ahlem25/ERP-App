package com.iss4u.erp.services.modules.stock.domain.models;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UniteMesure {
private String nom;
    private Boolean estNombreEntier;
}
