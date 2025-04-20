package com.iss4u.erp.services.modules.stock.domain.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LivreInventaire {
@ManyToOne private Article article;
    @ManyToOne private Entrepot entrepot;
    @ManyToOne private Lot lot;
    @ManyToOne private NumeroSerie numeroSerie;
    @ManyToOne private EcritureStock mouvement;
    private Double quantite;
    private Date date;
}
