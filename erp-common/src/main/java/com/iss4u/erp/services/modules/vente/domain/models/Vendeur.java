package com.iss4u.erp.services.modules.vente.domain.models;

import com.iss4u.erp.services.modules.vente.domain.client.models.Client;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vendeur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private Float commission;
    private String equipeParente;

    @ManyToOne
    private Societe societe;

    @OneToMany(mappedBy = "vendeur")
    private List<ObjectifCommercial> objectifs;

    @OneToMany(mappedBy = "vendeur")
    private List<Client> clients;
}
