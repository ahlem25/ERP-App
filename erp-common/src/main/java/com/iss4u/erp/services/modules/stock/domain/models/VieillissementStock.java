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
public class VieillissementStock {
@ManyToOne private Article article;
    @ManyToOne private Entrepot entrepot;
    private Double stock30j;
    private Double stock60j;
    private Double stock90j;
}
