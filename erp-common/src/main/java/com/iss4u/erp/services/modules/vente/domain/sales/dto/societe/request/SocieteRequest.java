package com.iss4u.erp.services.modules.vente.domain.sales.dto.societe.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SocieteRequest {
    private String nom;
    private String adresse;
    private String pays;
    private String deviseParDefaut;
    private String logo;
    private List<ProfilPDV> profilsPDV;
    private List<FactureVente> factures;
    private List<Vendeur> vendeurs;
    private List<Opportunite> opportunites;


}