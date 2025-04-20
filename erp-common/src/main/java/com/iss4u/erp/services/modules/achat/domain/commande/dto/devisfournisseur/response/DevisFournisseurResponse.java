package com.iss4u.erp.services.modules.achat.domain.commande.dto.devisfournisseur.response;

import com.iss4u.erp.services.modules.achat.domain.commande.models.Item;
import com.iss4u.erp.services.modules.achat.domain.commande.models.TaxesFrais;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.iss4u.erp.services.modules.achat.domain.fournisseur.models.Fournisseur;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DevisFournisseurResponse {
    private Long id;
    private String serie;
    private Double montantTotal;
    private Fournisseur fournisseur;
    private List<Item> articles;
    private List<TaxesFrais> taxes;


}