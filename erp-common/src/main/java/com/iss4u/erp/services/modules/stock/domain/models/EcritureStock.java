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
public class EcritureStock {
private String typeEntree;
    @ManyToOne private Entrepot entrepotSource;
    @ManyToOne private Entrepot entrepotCible;
    @ManyToOne private Article articles;
    private Double quantites;
    @ManyToOne private UniteMesure unitesMesure;
}
