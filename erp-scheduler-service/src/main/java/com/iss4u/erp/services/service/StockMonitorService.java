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
            // 1. Find a default supplier or the first available one
            Fournisseur fournisseur = findDefaultSupplier(article);
            if (fournisseur == null) {
                log.error("No supplier found for article: {}", article.getNomArticle());
                return;
            }
            
            // 2. Create the order item
            Item item = createOrderItem(article);
            
            // 3. Create the purchase order
            CommandeAchat commandeAchat = createCommandeAchat(fournisseur, item);
            
            // 4. Save the order
            CommandeAchat savedCommande = commandeAchatRepository.save(commandeAchat);
            
            log.info("Purchase order created successfully - ID: {}, Series: {}, Supplier: {}", 
                    savedCommande.getId(), savedCommande.getSerie(), fournisseur.getNomFournisseur());
            
        } catch (Exception e) {
            log.error("Error creating purchase order for article: {}", 
                     article.getNomArticle(), e);
        }
        
        log.info("====== End Purchase process =========");
    }
    
    private Fournisseur findDefaultSupplier(Article article) {
        // Find a default supplier or the first available one
        List<Fournisseur> fournisseurs = fournisseurRepository.findAll();
        if (fournisseurs.isEmpty()) {
            log.warn("No suppliers found in database");
            return null;
        }
        
        // For now, return the first available supplier
        // TODO: Implement more sophisticated logic to choose the right supplier
        return fournisseurs.get(0);
    }
    
    private Item createOrderItem(Article article) {
        Item item = new Item();
        item.setArticle(article);
        item.setQuantite((double) ORDER_QUANTITY);
        item.setPrixUnitaire(article.getPrixVente()); // to verify
        item.setCommentaire("Automatic order - Low stock detected");
        return item;
    }
    
    private CommandeAchat createCommandeAchat(Fournisseur fournisseur, Item item) {
        CommandeAchat commandeAchat = new CommandeAchat();
        
        // Generate a unique series number
        String serie = generateSerieNumber();
        commandeAchat.setSerie(serie);
        commandeAchat.setConditionsDeLivraison(DEFAULT_CONDITIONS);
        commandeAchat.setFournisseur(fournisseur);
        
        // Add the item to the order
        List<Item> items = new ArrayList<>();
        items.add(item);
        commandeAchat.setArticlesCommandes(items);
        
        return commandeAchat;
    }
    
    private String generateSerieNumber() {
        // Generate a series number based on date and counter
        String datePrefix = LocalDate.now().toString().replace("-", "");
        long count = commandeAchatRepository.count() + 1;
        return DEFAULT_SERIE + "-" + datePrefix + "-" + String.format("%04d", count);
    }
    
    /**
     * Checks if a purchase order already exists for a given article
     * @param article The article to check
     * @return true if an order already exists, false otherwise
     */
    public boolean hasExistingPurchaseOrder(Article article) {
        try {
            // Use a native query to avoid LazyInitializationException
            Long count = commandeAchatRepository.countByArticleIdAndComment(article.getId(), "Automatic order");
            
            if (count > 0) {
                log.info("Existing purchase order found for article: {} (Count: {})", 
                        article.getNomArticle(), count);
                return true;
            }
            
            return false;
        } catch (Exception e) {
            log.error("Error checking existing orders for article: {}", 
                     article.getNomArticle(), e);
            return false; // In case of error, consider there is no existing order
        }
    }
    
    /**
     * Checks if a pending purchase order exists for an article
     * @param article The article to check
     * @return true if a pending order exists, false otherwise
     */
    public boolean hasPendingPurchaseOrder(Article article) {
        try {
            // Use a native query to avoid LazyInitializationException
            Long count = commandeAchatRepository.countByArticleIdAndComment(article.getId(), "Automatic order");
            
            if (count > 0) {
                log.info("Pending purchase order found for article: {} (Count: {})", 
                        article.getNomArticle(), count);
                return true;
            }
            
            return false;
        } catch (Exception e) {
            log.error("Error checking pending orders for article: {}", 
                     article.getNomArticle(), e);
            return false;
        }
    }
}
