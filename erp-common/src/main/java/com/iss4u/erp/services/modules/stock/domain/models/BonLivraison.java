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

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BonLivraison {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Colonne ignorée pour éviter les relations circulaires avec ListePrix
    @JsonIgnore
    @Column(name = "liste_prix_id", insertable = false, updatable = false)
    private Long listePrixId;
    
    @ManyToOne
    @JoinColumn(name = "article_id")
    @JsonBackReference(value = "article-bon-livraisons")
    private Article articlesLivres;
    
    @ManyToOne
    @JoinColumn(name = "taxe_id")
    @JsonBackReference(value = "taxe-bon-livraisons")
    private Taxe taxes;
}
