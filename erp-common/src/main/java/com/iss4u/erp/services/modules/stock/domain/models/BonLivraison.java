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
public class BonLivraison {
@ManyToOne private Client client;
    @ManyToOne private Article articlesLivres;
    @ManyToOne private Taxes taxes;
}
