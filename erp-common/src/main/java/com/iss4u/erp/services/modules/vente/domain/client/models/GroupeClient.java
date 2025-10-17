package com.iss4u.erp.services.modules.vente.domain.client.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class GroupeClient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String modeleConditionsPaiementParDefaut;
    private boolean estGroupe;

    @OneToMany(mappedBy = "groupe")
    @JsonManagedReference(value = "groupe-client-clients")
    private List<Client> clients;
}