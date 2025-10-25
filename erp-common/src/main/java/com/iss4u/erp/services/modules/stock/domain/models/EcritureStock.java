package com.iss4u.erp.services.modules.stock.domain.models;

import com.iss4u.erp.services.modules.achat.domain.common.models.Article;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class EcritureStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String typeEntree;
    @ManyToOne
    @JoinColumn(name = "entrepot_source_id")
    @JsonIgnore
    private Entrepot entrepotSource;
    
    @ManyToOne
    @JoinColumn(name = "entrepot_cible_id")
    @JsonIgnore
    private Entrepot entrepotCible;
    
    @ManyToOne
    @JoinColumn(name = "article_id")
    @JsonIgnore
    private Article article;
    
    private Double quantites;
    
    @ManyToOne
    @JoinColumn(name = "unite_mesure_id")
    @JsonIgnore
    private UniteMesure unitesMesure;
    
    @OneToMany(mappedBy = "mouvement", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<LivreInventaire> livreInventaires;
}
