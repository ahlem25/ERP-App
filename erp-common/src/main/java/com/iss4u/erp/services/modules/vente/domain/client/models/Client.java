package com.iss4u.erp.services.modules.vente.domain.client.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.iss4u.erp.services.modules.achat.domain.common.models.ListePrix;
import com.iss4u.erp.services.modules.core.domain.models.Utilisateur;
import com.iss4u.erp.services.modules.stock.domain.models.BonLivraison;
import com.iss4u.erp.services.modules.vente.domain.billing.models.CommandeClient;
import com.iss4u.erp.services.modules.vente.domain.billing.models.Devis;
import com.iss4u.erp.services.modules.vente.domain.billing.models.FactureVente;
import com.iss4u.erp.services.modules.vente.domain.payment.models.MethodePaiement;
import com.iss4u.erp.services.modules.vente.domain.sales.models.Vendeur;
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
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String type;
    private String devise;
    private String adresse;
    private String siteWeb;
    private Boolean estClientInterne;
    @Column(length = 2000)
    private String detailsClient;
    @ManyToOne
    @JoinColumn(name = "groupe_client_id")
    @JsonBackReference(value = "groupe-clients")
    private GroupeClient groupe;



    @OneToMany(mappedBy = "client")
    @JsonManagedReference(value = "client-commandes")
    private List<CommandeClient> commandes;


    @OneToMany(mappedBy = "client")
    @JsonManagedReference
    private List<Devis> devis;



    @OneToMany(mappedBy = "client")
    @JsonManagedReference
    private List<FactureVente> factures;


    @ManyToOne
    @JoinColumn(name = "liste_prix_id")
    @JsonBackReference(value = "listePrix-clients")
    private ListePrix listePrix;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "client-bonlivraison")
    private List<BonLivraison> bonLivraisons;


}
