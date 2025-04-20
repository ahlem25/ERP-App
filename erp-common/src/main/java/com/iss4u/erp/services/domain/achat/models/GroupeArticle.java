package com.iss4u.erp.services.domain.achat.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupeArticle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomDuGroupe;
    private Double tauxTvaParDefaut;
    private String imageDuGroupe;
    @ManyToOne
    private GroupeArticle groupeParent;

    @OneToMany(mappedBy = "groupeArticle")
    private List<Article> articles;
}

