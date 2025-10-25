package com.iss4u.erp.services.modules.vente.domain.billing.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.iss4u.erp.services.modules.vente.domain.payment.models.Paiement;
import com.iss4u.erp.services.modules.vente.domain.sales.models.Societe;
import com.iss4u.erp.services.modules.vente.domain.sales.models.Taxe;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class FactureVente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numero;
    private Date dateEcheance;
    private Float montantTtc;

    @ManyToOne
    @JoinColumn(name = "societe_id")
    @JsonBackReference(value = "societe-factures")
    private Societe societe;

    @OneToMany(mappedBy = "facture")
    @JsonManagedReference(value = "facture-paiements")
    private List<Paiement> paiements;

    @ManyToMany(mappedBy = "factures")
    @JsonBackReference(value = "facture-taxes")
    private List<Taxe> taxes;

    @OneToMany(mappedBy = "facture")
    @JsonManagedReference(value = "facture-commandes")
    private List<CommandeClient> commandes;

}
