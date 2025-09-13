package com.iss4u.erp.services.modules.core.domain.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "utilisateurs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String prenom;

    @Column(nullable = false)
    private String nomFamille;

    private Boolean actif = true;

    private Date derniereMaj;

    @OneToMany
    private List<Role> roles;

    @OneToMany
    private List<Autorisation> autorisations;

    @OneToMany
    private List<Document> documents;

    @OneToMany
    private List<Commentaire> commentaires;

    // Méthode utilitaire
    public String getNomComplet() {
        return prenom + " " + nomFamille;
    }
}
