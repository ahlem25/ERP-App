package com.iss4u.erp.services.modules.core.domain.models;
import com.iss4u.erp.services.modules.vente.domain.client.models.Client;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String nomUtilisateur;
    private String prenom;
    private String secondNom;
    private String nomFamille;
    private String langue;
    private String fuseauHoraire;
    private Boolean actif;
    private Boolean envoyerEmailBienvenue;
    private Date derniereMaj;
    @OneToMany
    private List<Role> roles;
    @OneToMany
    private List<Autorisation> autorisations;
    @OneToMany
    private List<Document> documents;
    @OneToMany
    private List<Commentaire> commentaires;



}
