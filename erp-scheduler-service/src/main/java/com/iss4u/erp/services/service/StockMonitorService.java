package com.iss4u.erp.services.service;

import com.iss4u.erp.services.modules.achat.domain.commande.models.CommandeAchat;
import com.iss4u.erp.services.modules.achat.domain.commande.models.Item;
import com.iss4u.erp.services.modules.achat.domain.commande.repository.CommandeAchatRepository;
import com.iss4u.erp.services.modules.achat.domain.common.models.Article;
import com.iss4u.erp.services.modules.achat.domain.common.repository.ArticleRepository;
import com.iss4u.erp.services.modules.achat.domain.fournisseur.models.Fournisseur;
import com.iss4u.erp.services.modules.achat.domain.fournisseur.repository.FournisseurRepository;
import com.iss4u.erp.services.modules.stock.domain.repository.SoldeStockRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class StockMonitorService {

    @Autowired
    SoldeStockRepository soldeStockRepository;

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    CommandeAchatRepository commandeAchatRepository;

    @Autowired
    FournisseurRepository fournisseurRepository;

    private static final int ORDER_QUANTITY = 20;
    private static final String DEFAULT_SERIE = "CMD";
    private static final String DEFAULT_CONDITIONS = "Livraison sous 7 jours";


    public int getQuantity(Article article){
       return soldeStockRepository.findStockByArticleId(article.getId()).orElse(0);
    }

    public List<Article> findByQuantityLessThan(int threshold){
        List<Article>  articles =  this.articleRepository.findAll();
        List<Article> lowStockProducts = new ArrayList<>();
        for (Article article:
             articles
             ) {
            int stock = soldeStockRepository.findStockByArticleId(article.getId()).orElse(0);;
            if(stock <= threshold){
                       lowStockProducts.add(article);
            }
        }
        return lowStockProducts;
    }

    public void createPurchaseOrder(Article article){
        log.info("====== Start Purchase process for article: {} =========", article.getNomArticle());
        
        try {
            // 1. Trouver un fournisseur par défaut ou le premier disponible
            Fournisseur fournisseur = findDefaultSupplier(article);
            if (fournisseur == null) {
                log.error("Aucun fournisseur trouvé pour l'article: {}", article.getNomArticle());
                return;
            }
            
            // 2. Créer l'item de commande
            Item item = createOrderItem(article);
            
            // 3. Créer la commande d'achat
            CommandeAchat commandeAchat = createCommandeAchat(fournisseur, item);
            
            // 4. Sauvegarder la commande
            CommandeAchat savedCommande = commandeAchatRepository.save(commandeAchat);
            
            log.info("Commande d'achat créée avec succès - ID: {}, Série: {}, Fournisseur: {}", 
                    savedCommande.getId(), savedCommande.getSerie(), fournisseur.getNomFournisseur());
            
        } catch (Exception e) {
            log.error("Erreur lors de la création de la commande d'achat pour l'article: {}", 
                     article.getNomArticle(), e);
        }
        
        log.info("====== End Purchase process =========");
    }
    
    private Fournisseur findDefaultSupplier(Article article) {
        // Chercher un fournisseur par défaut ou le premier disponible
        List<Fournisseur> fournisseurs = fournisseurRepository.findAll();
        if (fournisseurs.isEmpty()) {
            log.warn("Aucun fournisseur trouvé dans la base de données");
            return null;
        }
        
        // Pour l'instant, retourner le premier fournisseur disponible
        // TODO: Implémenter une logique plus sophistiquée pour choisir le bon fournisseur
        return fournisseurs.get(0);
    }
    
    private Item createOrderItem(Article article) {
        Item item = new Item();
        item.setArticle(article);
        item.setQuantite((double) ORDER_QUANTITY);
        item.setPrixUnitaire(article.getPrixVente()); // to verify
        item.setCommentaire("Commande automatique - Stock faible détecté");
        return item;
    }
    
    private CommandeAchat createCommandeAchat(Fournisseur fournisseur, Item item) {
        CommandeAchat commandeAchat = new CommandeAchat();
        
        // Générer un numéro de série unique
        String serie = generateSerieNumber();
        commandeAchat.setSerie(serie);
        commandeAchat.setConditionsDeLivraison(DEFAULT_CONDITIONS);
        commandeAchat.setFournisseur(fournisseur);
        
        // Ajouter l'item à la commande
        List<Item> items = new ArrayList<>();
        items.add(item);
        commandeAchat.setArticlesCommandes(items);
        
        return commandeAchat;
    }
    
    private String generateSerieNumber() {
        // Générer un numéro de série basé sur la date et un compteur
        String datePrefix = LocalDate.now().toString().replace("-", "");
        long count = commandeAchatRepository.count() + 1;
        return DEFAULT_SERIE + "-" + datePrefix + "-" + String.format("%04d", count);
    }
}
