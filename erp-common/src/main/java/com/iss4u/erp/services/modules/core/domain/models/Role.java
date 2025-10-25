package com.iss4u.erp.services.modules.core.domain.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomRole;
    private String pageAccueil;
    private String route;
    private String domaineRestreint;
    private Boolean desactive;
    private Boolean isCustom;
    private Boolean accesBureau;
    private Boolean authDoubleFacteur;
    @OneToMany(mappedBy = "role")
    @JsonManagedReference(value = "role-profils")
    private List<ProfilRole> profils;
    
    @OneToMany(mappedBy = "role")
    @JsonManagedReference(value = "role-modules")
    private List<ModuleProfile> modules;
    
    @OneToMany(mappedBy = "role")
    @JsonManagedReference(value = "role-autorisations")
    private List<Autorisation> autorisations;
    
    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    @JsonBackReference(value = "utilisateur-roles")
    private Utilisateur utilisateur;
}
