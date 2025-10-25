package com.iss4u.erp.services.modules.achat.domain.commande.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.iss4u.erp.services.modules.achat.domain.common.models.Article;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "article_id")
    @JsonBackReference(value = "article-items")
    private Article article;
    
    @ManyToOne
    @JoinColumn(name = "commande_achat_id")
    @JsonBackReference(value = "commande-achat-items")
    private CommandeAchat commandeAchat;

    private Double quantite;
    private Double prixUnitaire;
    private String commentaire;
}

