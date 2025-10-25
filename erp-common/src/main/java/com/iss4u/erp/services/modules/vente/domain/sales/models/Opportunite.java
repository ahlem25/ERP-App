package com.iss4u.erp.services.modules.vente.domain.sales.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.iss4u.erp.services.modules.vente.domain.client.models.Client;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Opportunite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private Float probabilite;
    private Float montantEstime;

    @ManyToOne
    @JoinColumn(name = "client_potentiel_id")
    @JsonBackReference(value = "client-opportunites")
    private Client clientPotentiel;

    @ManyToOne
    @JoinColumn(name = "societe_id")
    @JsonBackReference(value = "societe-opportunites")
    private Societe societe;
}
