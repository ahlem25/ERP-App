package com.iss4u.erp.services.modules.vente.domain.sales.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.iss4u.erp.services.modules.vente.domain.payment.models.MethodePaiement;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfilPDV {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String methodesPaiementAutorisees;
    private String entrepotAssocie;
    private String parametresImpression;

    @ManyToOne
    @JoinColumn(name = "societe_id")
    @JsonBackReference(value = "societe-profils-pdv")
    private Societe societe;

    @OneToMany(mappedBy = "profilPDV")
    @JsonManagedReference(value = "profil-pdv-methodes-paiement")
    private List<MethodePaiement> methodesPaiement;
}
