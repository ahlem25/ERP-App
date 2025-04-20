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
public class GroupeFournisseurs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomDuGroupe;
    private Boolean estUnGroupe;
    private Double limiteDeCredit;

    @ManyToOne
    private GroupeFournisseurs groupeParent;

    @OneToMany(mappedBy = "groupeDeFournisseurs")
    private List<Fournisseur> fournisseurs;
}
