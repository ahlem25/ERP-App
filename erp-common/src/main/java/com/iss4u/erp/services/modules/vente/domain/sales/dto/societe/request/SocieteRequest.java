package com.iss4u.erp.services.modules.vente.domain.sales.dto.societe.request;

import com.iss4u.erp.services.modules.vente.domain.billing.models.FactureVente;
import com.iss4u.erp.services.modules.vente.domain.sales.models.Opportunite;
import com.iss4u.erp.services.modules.vente.domain.sales.models.ProfilPDV;
import com.iss4u.erp.services.modules.vente.domain.sales.models.Vendeur;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SocieteRequest {
    private String nom;
    private String adresse;
    private String pays;
    private String deviseParDefaut;
    @Column(name = "numero_identification_fiscale")
    private String numeroIdentificationFiscale;

    private String domaine;

    @Column(name = "date_creation")
    private LocalDate dateCreation;

    @Column(name = "est_groupe")
    private Boolean estGroupe = false;
    private List<ProfilPDV> profilsPDV;
    private List<FactureVente> factures;
    private List<Vendeur> vendeurs;
    private List<Opportunite> opportunites;


}