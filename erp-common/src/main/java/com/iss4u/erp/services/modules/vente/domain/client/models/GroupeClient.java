package com.iss4u.erp.services.modules.vente.domain.client.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.iss4u.erp.services.modules.achat.domain.common.models.ListePrix;
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

    @ManyToOne
    @JoinColumn(name = "liste_prix_id")
    @JsonBackReference(value = "listePrix-groupesClients")
    private ListePrix listePrix;
    @OneToMany(mappedBy = "groupe")
    @JsonManagedReference(value = "groupe-clients") // ✅ Fournit la racine pour la sérialisation
    private List<Client> clients;
} 