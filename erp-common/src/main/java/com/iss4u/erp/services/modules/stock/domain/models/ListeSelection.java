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
public class ListeSelection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Article articlesAPrelever;
    @ManyToOne
    private Entrepot entrepotSource;
    private Double quantitesPrelevees;
}
