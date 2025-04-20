package com.iss4u.erp.services.modules.achat.domain.commande.models;

import com.iss4u.erp.services.modules.achat.domain.common.models.Article;
import com.iss4u.erp.services.modules.stock.domain.models.Entrepot;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DemandeMateriel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String commentaire;
    private String type;

    @ManyToOne
    private Entrepot entrepotCible;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;
}