package com.iss4u.erp.services.modules.stock.domain.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.iss4u.erp.services.modules.achat.domain.common.models.Article;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NumeroSerie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String idSerie;
    @ManyToOne
    @JoinColumn(name = "article_id")
    @JsonBackReference(value = "article-numero-series")
    private Article article;
    private Double garantie;
    
    @OneToMany(mappedBy = "numeroSerie")
    @JsonManagedReference(value = "numero-serie-livre-inventaires")
    private List<LivreInventaire> livreInventaires;
}
