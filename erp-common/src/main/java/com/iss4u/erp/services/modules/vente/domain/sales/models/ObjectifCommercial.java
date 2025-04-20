package com.iss4u.erp.services.modules.vente.domain.sales.models;

import com.iss4u.erp.services.modules.achat.domain.common.models.GroupeArticle;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ObjectifCommercial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String periode;
    private Float montantCible;

    @ManyToOne
    private Vendeur vendeur;

    @ManyToOne
    private GroupeArticle groupeArticle;
}
