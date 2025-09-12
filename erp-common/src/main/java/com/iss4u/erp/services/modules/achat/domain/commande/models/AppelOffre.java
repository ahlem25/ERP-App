package com.iss4u.erp.services.modules.achat.domain.commande.models;

import com.iss4u.erp.services.modules.achat.domain.fournisseur.models.Fournisseur;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppelOffre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serie;
    private String modeleEmail;

    @ManyToMany
    private List<Fournisseur> fournisseursContactes;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Item> articlesDemandes;
}


