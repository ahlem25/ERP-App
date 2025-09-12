package com.iss4u.erp.services.modules.achat.domain.fournisseur.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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


    @OneToMany(mappedBy = "groupeDeFournisseurs")
    @JsonManagedReference(value = "groupeFournisseurs-fournisseurs")
    private List<Fournisseur> fournisseurs;


}
