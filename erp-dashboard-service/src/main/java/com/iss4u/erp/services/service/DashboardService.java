package com.iss4u.erp.services.service;

import com.iss4u.erp.services.modules.achat.domain.commande.repository.CommandeAchatRepository;
import com.iss4u.erp.services.modules.achat.domain.common.repository.ArticleRepository;
import com.iss4u.erp.services.modules.vente.domain.billing.repository.CommandeClientRepository;
import com.iss4u.erp.services.modules.vente.domain.client.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashboardService {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    CommandeClientRepository commandeClientRepository;

    @Autowired
    CommandeAchatRepository commandeAchatRepository;

    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        
        try {
            // Récupération des statistiques depuis la base de données
            long nbClients = clientRepository.count();
            long nbArticles = articleRepository.count();
            long nbVentes = commandeClientRepository.count();
            long nbCommandes = commandeAchatRepository.count();

            // Préparation de la réponse selon le format demandé
            stats.put("totalVentes", nbVentes);
            stats.put("totalCommandes", nbCommandes);
            stats.put("totalProduits", nbArticles);
            stats.put("totalClients", nbClients);
            
            // Données pour les graphiques
            stats.put("revenus", new int[]{35, 42, 28, 51, 45, 57, 38, 62, 48, 54, 49, 61});
            stats.put("commandes", new int[]{75, 68, 82, 91});
            stats.put("ventes", new int[]{12, 19, 15, 25, 22, 18, 24});
            
            // Métadonnées
            stats.put("lastUpdated", System.currentTimeMillis());
            stats.put("status", "success");
            
        } catch (Exception e) {
            // En cas d'erreur, retourner des valeurs par défaut
            stats.put("totalVentes", 0);
            stats.put("totalCommandes", 0);
            stats.put("totalProduits", 0);
            stats.put("totalClients", 0);
            stats.put("revenus", new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
            stats.put("commandes", new int[]{0, 0, 0, 0});
            stats.put("ventes", new int[]{0, 0, 0, 0, 0, 0, 0});
            stats.put("lastUpdated", System.currentTimeMillis());
            stats.put("status", "error");
            stats.put("error", e.getMessage());
        }
        
        return stats;
    }

}
