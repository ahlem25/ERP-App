package com.iss4u.erp.services.modules.achat.domain.common.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.iss4u.erp.services.modules.achat.domain.commande.models.DemandeMateriel;
import com.iss4u.erp.services.modules.achat.domain.common.models.PrixArticle;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ListePrix {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nomListeDePrix;

    @Column(nullable = false)
    private String devise;

    private boolean actif;

    private boolean achat;

    private boolean vente;

    private boolean prixIndependantUniteMesure;

    @OneToMany(mappedBy = "listeDePrix", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "liste-prix-prix-articles")
    private List<PrixArticle> prixArticles;

}
