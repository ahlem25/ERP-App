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
public class ReceptionAchat {
@ManyToOne private Fournisseur fournisseur;
    @ManyToOne private Article articlesRecus;
    private Double quantitesAcceptees;
    private Double quantitesRejetees;
}
