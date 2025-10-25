package com.iss4u.erp.services.modules.achat.domain.common.service;

import com.iss4u.erp.services.modules.achat.domain.common.models.Article;
import com.iss4u.erp.services.modules.stock.domain.models.BonLivraison;
import com.iss4u.erp.services.modules.achat.domain.common.dto.article.response.BonLivraisonInfo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleBonLivraisonService {
    
    /**
     * Récupère les informations des bons de livraison liés à un article
     * sans créer de référence circulaire
     */
    public List<BonLivraisonInfo> getBonLivraisonsForArticle(Article article) {
        // Cette méthode sera implémentée dans le service qui gère les bonLivraisons
        // Pour l'instant, retourner une liste vide
        return List.of();
    }
    
    /**
     * Convertit un BonLivraison en BonLivraisonInfo
     */
    public BonLivraisonInfo convertToBonLivraisonInfo(BonLivraison bonLivraison) {
        if (bonLivraison == null) return null;
        
        BonLivraisonInfo info = new BonLivraisonInfo();
        info.setId(bonLivraison.getId());
        info.setSerie(bonLivraison.getSerie());
        info.setDate(bonLivraison.getDate());
        info.setHeure(bonLivraison.getHeure());
        info.setEstRetour(bonLivraison.isEstRetour());
        info.setDevise(bonLivraison.getDevise());
        info.setIgnorerRegleTarification(bonLivraison.isIgnorerRegleTarification());
        info.setQuantiteTotale(bonLivraison.getQuantiteTotale());
        info.setMontantTotal(bonLivraison.getMontantTotal());
        
        // Informations de la taxe
        if (bonLivraison.getTaxes() != null) {
            info.setTaxeId(bonLivraison.getTaxes().getId());
            info.setTaxeNom(bonLivraison.getTaxes().getNom());
        }
        
        return info;
    }
}
