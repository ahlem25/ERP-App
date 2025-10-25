package com.iss4u.erp.services.modules.stock.domain.models;

import com.iss4u.erp.services.modules.achat.domain.common.models.Article;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LivreInventaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    @JsonIgnore
    private Article article;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entrepot_id")
    @JsonIgnore
    private Entrepot entrepot;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lot_id")
    @JsonIgnore
    private Lot lot;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mouvement_id")
    @JsonIgnore
    private EcritureStock mouvement;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "numero_serie_id")
    @JsonIgnore
    private NumeroSerie numeroSerie;
    
    private Double quantite;
    private String date;
}