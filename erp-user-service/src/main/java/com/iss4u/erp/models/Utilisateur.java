package com.iss4u.erp.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "utilisateurs")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Utilisateur {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "prenom", nullable = false)
    private String prenom;
    
    @Column(name = "nom_famille", nullable = false)
    private String nomFamille;
    
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    
    @Column(name = "password")
    private String password;
    
    @Column(name = "actif")
    private Boolean actif = true;
    
    @Column(name = "derniere_maj")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime derniereMaj;
    
    // Constructeurs
    public Utilisateur() {}
    
    public Utilisateur(String prenom, String nomFamille, String email, String password, Boolean actif) {
        this.prenom = prenom;
        this.nomFamille = nomFamille;
        this.email = email;
        this.password = password;
        this.actif = actif;
        this.derniereMaj = LocalDateTime.now();
    }
    
    // Getters et Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getPrenom() {
        return prenom;
    }
    
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    
    public String getNomFamille() {
        return nomFamille;
    }
    
    public void setNomFamille(String nomFamille) {
        this.nomFamille = nomFamille;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public Boolean isActif() {
        return actif;
    }
    
    public void setActif(Boolean actif) {
        this.actif = actif;
    }
    
    public LocalDateTime getDerniereMaj() {
        return derniereMaj;
    }
    
    public void setDerniereMaj(LocalDateTime derniereMaj) {
        this.derniereMaj = derniereMaj;
    }
    
    // Méthodes utilitaires
    public String getNomComplet() {
        return prenom + " " + nomFamille;
    }
    
    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", prenom='" + prenom + '\'' +
                ", nomFamille='" + nomFamille + '\'' +
                ", email='" + email + '\'' +
                ", actif=" + actif +
                ", derniereMaj=" + derniereMaj +
                '}';
    }
}
