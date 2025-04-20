package com.iss4u.erp.services.modules.vente.domain.client.models;

import com.iss4u.erp.services.modules.vente.domain.billing.models.CommandeClient;
import com.iss4u.erp.services.modules.vente.domain.billing.models.Devis;
import com.iss4u.erp.services.modules.vente.domain.billing.models.FactureVente;
import com.iss4u.erp.services.modules.vente.domain.sales.models.Vendeur;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String groupeClient;
    private String gestionnaireCompte;
    private String conditionsPaiement;

    @ManyToOne
    private GroupeClient groupe;

    @OneToMany(mappedBy = "client")
    private List<CommandeClient> commandes;

    @OneToMany(mappedBy = "client")
    private List<FactureVente> factures;

    @OneToMany(mappedBy = "client")
    private List<Devis> devis;

    @ManyToOne
    private Vendeur vendeur;
}
