package com.iss4u.erp.services.modules.achat.domain.fournisseur.dto.groupefournisseurs.request;

import com.iss4u.erp.services.modules.achat.domain.fournisseur.models.Fournisseur;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.iss4u.erp.services.modules.achat.domain.fournisseur.models.GroupeFournisseurs;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupeFournisseursRequest {
    private String nomDuGroupe;
    private Boolean estUnGroupe;
    private Double limiteDeCredit;

    private List<Fournisseur> fournisseurs;


}