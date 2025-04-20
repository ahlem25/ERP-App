package com.iss4u.erp.services.modules.achat.domain.fournisseur.dto.fournisseur.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.iss4u.erp.services.modules.achat.domain.fournisseur.models.GroupeFournisseurs;
import com.iss4u.erp.services.modules.achat.domain.common.models.ListePrix;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FournisseurRequest {
    private String nomDuFournisseur;
    private String pays;
    private String deviseDeFacturation;
    private String typeDeFournisseur;
    private Boolean estTransporteur;
    private GroupeFournisseurs groupeDeFournisseurs;
    private ListePrix listeDePrix;


}