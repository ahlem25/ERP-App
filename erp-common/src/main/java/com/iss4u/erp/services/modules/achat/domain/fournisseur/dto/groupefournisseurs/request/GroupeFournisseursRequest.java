package com.iss4u.erp.services.modules.achat.domain.fournisseur.dto.groupefournisseurs.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iss4u.erp.services.modules.achat.domain.fournisseur.models.Fournisseur;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.iss4u.erp.services.modules.achat.domain.fournisseur.models.GroupeFournisseurs;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupeFournisseursRequest {
    private String nomDuGroupe;
    private Boolean estUnGroupe;
    private Double limiteDeCredit;
    
    @JsonIgnore
    private GroupeFournisseurs groupeParent;
    
    @JsonIgnore
    private List<Fournisseur> fournisseurs;
}