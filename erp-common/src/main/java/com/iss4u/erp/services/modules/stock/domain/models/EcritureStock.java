package com.iss4u.erp.services.modules.stock.domain.models;

import com.iss4u.erp.services.modules.achat.domain.common.models.Article;
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
public class EcritureStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String typeEntree;
    @ManyToOne
    private Entrepot entrepotSource;
    @ManyToOne
    private Entrepot entrepotCible;
    @ManyToOne
    private Article articles;
    private Double quantites;
    @ManyToOne
    private UniteMesure unitesMesure;
}
