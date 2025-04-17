package com.iss4u.erp.services.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrixArticle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double prix;
    private Double quantiteMinimale;
    private String devise;
    private String reference;

    @ManyToOne
    private Article article;

    @ManyToOne
    private ListePrix listeDePrix;

    @ManyToOne
    private Fournisseur fournisseur;
}

