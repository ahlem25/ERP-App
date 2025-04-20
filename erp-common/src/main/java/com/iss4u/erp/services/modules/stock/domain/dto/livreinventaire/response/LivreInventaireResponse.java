package com.iss4u.erp.services.modules.stock.domain.dto.livreinventaire.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.iss4u.erp.services.modules.achat.domain.common.models.Article;
import com.iss4u.erp.services.modules.stock.domain.models.Entrepot;
import com.iss4u.erp.services.modules.stock.domain.models.Lot;
import com.iss4u.erp.services.modules.stock.domain.models.NumeroSerie;
import com.iss4u.erp.services.modules.stock.domain.models.EcritureStock;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LivreInventaireResponse {
    private Long id;
    private Article article;
    private Entrepot entrepot;
    private Lot lot;
    private NumeroSerie numeroSerie;
    private EcritureStock mouvement;
    private Double quantite;
    private Date date;


}