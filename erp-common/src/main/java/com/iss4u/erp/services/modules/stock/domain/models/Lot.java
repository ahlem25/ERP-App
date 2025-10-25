package com.iss4u.erp.services.modules.stock.domain.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.iss4u.erp.services.modules.achat.domain.common.models.Article;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "article_id")
    @JsonBackReference(value = "article-lots")
    private Article article;
    private Date dateProduction;
    private Date dateExpiration;
    private Double garantie;
    
    @OneToMany(mappedBy = "lot")
    @JsonManagedReference(value = "lot-livre-inventaires")
    private List<LivreInventaire> livreInventaires;
}
