package com.iss4u.erp.services.modules.vente.domain.client.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.iss4u.erp.services.modules.vente.domain.billing.models.CommandeClient;
import com.iss4u.erp.services.modules.vente.domain.billing.models.Devis;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "client")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true, value = {"articles"})
@org.hibernate.annotations.DynamicUpdate
@org.hibernate.annotations.DynamicInsert
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String adresse;
    private String detailsClient;
    private String devise;
    private Boolean estClientInterne;
    private String siteWeb;
    private String type;



    @ManyToOne
    @JoinColumn(name = "groupe_client_id")
    @JsonBackReference(value = "groupe-client-clients")
    private GroupeClient groupe;

    @OneToMany(mappedBy = "client")
    @JsonManagedReference(value = "client-commandes")  // ✅ Bien joué ici
    private List<CommandeClient> commandes;


    @OneToMany(mappedBy = "client")
    @JsonManagedReference("client-devis")
    private List<Devis> devis;


}
