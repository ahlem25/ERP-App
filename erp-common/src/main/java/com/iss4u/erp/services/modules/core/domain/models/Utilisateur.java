package com.iss4u.erp.services.modules.core.domain.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    
    @OneToMany(mappedBy = "utilisateur")
    @JsonManagedReference(value = "utilisateur-roles")
    private List<Role> roles;
    
    @OneToMany(mappedBy = "utilisateur")
    @JsonManagedReference(value = "utilisateur-autorisations")
    private List<Autorisation> autorisations;
    
    // Relation supprimée car Document n'a pas de propriété utilisateur
    // @OneToMany(mappedBy = "utilisateur")
    @JsonManagedReference(value = "utilisateur-documents")
    // private List<Document> documents;
    
    @OneToMany(mappedBy = "utilisateur")
    @JsonManagedReference(value = "utilisateur-commentaires")
    private List<Commentaire> commentaires;
    
    // Méthode utilitaire
    public String getNomComplet() {
        return prenom + " " + nomFamille;
    }
}
