package com.iss4u.erp.services.modules.stock.domain.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iss4u.erp.services.modules.achat.domain.common.models.Article;
import com.iss4u.erp.services.modules.vente.domain.sales.models.Taxe;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BonLivraison {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serie;
    private LocalDate date;
    private LocalTime heure;
    private boolean estRetour;
    private String devise;
    private boolean ignorerRegleTarification;
    private BigDecimal quantiteTotale;
    private BigDecimal montantTotal;
    @ManyToOne
    @JoinColumn(name = "article_id")
    @JsonBackReference(value = "article-bon-livraisons")
    private Article article;

    @ManyToOne
    @JoinColumn(name = "taxe_id")
    @JsonBackReference(value = "taxe-bon-livraisons")
    private Taxe taxe;
}
