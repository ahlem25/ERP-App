package com.iss4u.erp.services.service;

import com.iss4u.erp.services.modules.achat.domain.commande.repository.CommandeAchatRepository;
import com.iss4u.erp.services.modules.achat.domain.common.models.Article;
import com.iss4u.erp.services.modules.achat.domain.common.repository.ArticleRepository;
import com.iss4u.erp.services.modules.stock.domain.repository.SoldeStockRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class StockMonitorService {

    @Autowired
    SoldeStockRepository soldeStockRepository;

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    CommandeAchatRepository commandeAchatRepository;


    private static final int ORDER_QUANTITY = 20;


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
        log.info("====== Start Purchase proccess =========");
        log.info("======  End Purchase proccess  =========");
    }
}
