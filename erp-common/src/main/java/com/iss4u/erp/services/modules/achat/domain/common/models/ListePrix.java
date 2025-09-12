package com.iss4u.erp.services.modules.achat.domain.common.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.iss4u.erp.services.modules.achat.domain.commande.models.DemandeMateriel;
import com.iss4u.erp.services.modules.achat.domain.common.models.PrixArticle;
import com.iss4u.erp.services.modules.achat.domain.fournisseur.models.Fournisseur;
import com.iss4u.erp.services.modules.stock.domain.models.BonLivraison;
import com.iss4u.erp.services.modules.vente.domain.client.models.Client;
import com.iss4u.erp.services.modules.vente.domain.client.models.GroupeClient;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListePrix {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nomListeDePrix;

    @Column(nullable = false)
    private String devise;

    private boolean actif;

    private boolean achat;

    private boolean vente;

    private boolean prixIndependantUniteMesure;

    @OneToMany(mappedBy = "listeDePrix")
    @JsonManagedReference(value = "listePrix-prixArticles")
    private List<PrixArticle> prixArticles;

    @OneToMany(mappedBy = "listePrix")
    @JsonManagedReference(value = "listePrix-clients")
    private List<Client> clients;

    @OneToMany(mappedBy = "listePrix")
    @JsonManagedReference(value = "listePrix-groupesClients")
    private List<GroupeClient> groupeClient;

    @OneToMany(mappedBy = "listeDePrix")
    @JsonManagedReference(value = "listePrix-fournisseurs")
    private List<Fournisseur> fournisseurs;




    @OneToMany(mappedBy = "listePrix")
    @JsonManagedReference(value = "listePrix-bonlivraisons")
    private List<BonLivraison> bonLivraisons;

}
