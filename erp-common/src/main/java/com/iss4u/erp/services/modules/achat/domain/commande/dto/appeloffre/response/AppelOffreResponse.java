package com.iss4u.erp.services.modules.achat.domain.commande.dto.appeloffre.response;

import com.iss4u.erp.services.modules.achat.domain.commande.models.Item;
import com.iss4u.erp.services.modules.achat.domain.fournisseur.models.Fournisseur;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppelOffreResponse {
    private Long id;
    private String serie;
    private String modeleEmail;
    private List<Fournisseur> fournisseursContactes;
    private List<Item> articlesDemandes;


}