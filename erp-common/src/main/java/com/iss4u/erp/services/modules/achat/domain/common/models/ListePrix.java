package com.iss4u.erp.services.modules.achat.domain.common.models;

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
public class ListePrix {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String devise;
    private Boolean actif;
    private LocalDate valideDu;
    private LocalDate valideJusquau;
    private Boolean pourAchat;
    private Boolean pourVente;

    @OneToMany(mappedBy = "listeDePrix")
    private List<PrixArticle> prixArticles;
}
