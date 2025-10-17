package com.iss4u.erp.services.modules.vente.domain.payment.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.iss4u.erp.services.modules.vente.domain.billing.models.FactureVente;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Paiement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private Float montant;
    private String methodePaiement;

    @ManyToOne
    @JoinColumn(name = "facture_id")
    @JsonBackReference(value = "facture-paiements") // <- ici c’est le back
    private FactureVente facture;


}
