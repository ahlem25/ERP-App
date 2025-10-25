package com.iss4u.erp.services.modules.core.domain.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Commentaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String contenu;
    private Date dateCreation;
    
    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    @JsonBackReference(value = "utilisateur-commentaires")
    private Utilisateur utilisateur;
    
    @ManyToOne
    @JoinColumn(name = "document_id")
    @JsonBackReference(value = "document-commentaires")
    private Document document;
}
