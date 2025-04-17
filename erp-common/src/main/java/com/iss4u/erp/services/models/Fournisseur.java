package com.iss4u.erp.services.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Fournisseur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomDuFournisseur;
    private String pays = "Tunisie";
    private String deviseDeFacturation;
    private String typeDeFournisseur;
    private Boolean estTransporteur;

    @ManyToOne
    private GroupeFournisseurs groupeDeFournisseurs;

    @ManyToOne
    private ListePrix listeDePrix;
}
