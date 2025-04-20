package com.iss4u.erp.services.modules.core.domain.models;

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
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String reference;
    private String typeDocument;
    private Boolean estStandard;
    private Boolean actif;
    private Date dateCreation;
    private Date dateDerniereModif;
    @OneToMany
    private List<Journal> journaux;
    @OneToMany
    private List<Activite> activites;
    @OneToMany
    private List<Commentaire> commentaires;
}
