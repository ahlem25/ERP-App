package com.iss4u.erp.services.modules.achat.domain.common.models;

import com.iss4u.erp.services.modules.achat.domain.fournisseur.models.Fournisseur;
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
public class FactureAchat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serie;
    private LocalDate dateDePaiement;
    private Double totalTtc;

    @ManyToOne
    private Fournisseur fournisseur;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Item> articlesFactures;
}


