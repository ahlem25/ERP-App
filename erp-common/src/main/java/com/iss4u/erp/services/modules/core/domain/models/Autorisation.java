package com.iss4u.erp.services.modules.core.domain.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Autorisation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String typeAutorisation;
    private String valeur;
    private Boolean appliquerTousDocuments;
    
    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    @JsonBackReference(value = "utilisateur-autorisations")
    private Utilisateur utilisateur;
    
    @ManyToOne
    @JoinColumn(name = "role_id")
    @JsonBackReference(value = "role-autorisations")
    private Role role;
}
