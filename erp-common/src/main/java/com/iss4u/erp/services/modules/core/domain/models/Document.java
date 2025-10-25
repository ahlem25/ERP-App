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
    @OneToMany(mappedBy = "document")
    @JsonManagedReference(value = "document-journaux")
    private List<Journal> journaux;
    
    @OneToMany(mappedBy = "document")
    @JsonManagedReference(value = "document-activites")
    private List<Activite> activites;
    
    @OneToMany(mappedBy = "document")
    @JsonManagedReference(value = "document-commentaires")
    private List<Commentaire> commentaires;
}
