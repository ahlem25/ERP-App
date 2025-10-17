package com.iss4u.erp.services.modules.stock.domain.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.iss4u.erp.services.modules.achat.domain.common.models.Article;
import jakarta.persistence.*;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "article_id")
    @JsonBackReference(value = "article-livreInventaire")
    private Article article;

    @ManyToOne
    @JoinColumn(name = "entrepot_id")
    @JsonBackReference(value = "entrepot-livreInventaire")
    private Entrepot entrepot;

    @ManyToOne
    @JoinColumn(name = "lot_id")
    @JsonBackReference(value = "lot-livreInventaire")
    private Lot lot;

    @ManyToOne
    @JoinColumn(name = "numero_serie_id")
    @JsonBackReference(value = "numeroSerie-livreInventaire")
    private NumeroSerie numeroSerie;

    @ManyToOne
    @JoinColumn(name = "mouvement_id")
    @JsonBackReference(value = "mouvement-livreInventaire")
    private EcritureStock mouvement;

    private Double quantite;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
}
