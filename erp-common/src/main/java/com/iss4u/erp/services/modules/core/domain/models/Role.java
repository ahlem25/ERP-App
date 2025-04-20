package com.iss4u.erp.services.modules.core.domain.models;

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
    @OneToMany
    private List<ProfilRole> profils;
    @OneToMany
    private List<ModuleProfile> modules;
    @OneToMany
    private List<Autorisation> autorisations;
}
