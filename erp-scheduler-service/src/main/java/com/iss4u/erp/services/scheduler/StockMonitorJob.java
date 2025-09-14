package com.iss4u.erp.services.scheduler;

import com.iss4u.erp.services.modules.achat.domain.common.models.Article;
import com.iss4u.erp.services.service.StockMonitorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class StockMonitorJob {

    @Autowired
    StockMonitorService stockMonitorService;

    private static final int STOCK_THRESHOLD = 10;

    /**
     * Runs every day at 00:00 and 12:00
     */
    //@Scheduled(cron = "0 0 0,12 * * ?")
    @Scheduled(cron = "0 * * * * ?")// Every minutes
    public void checkStockLevels() {
        log.info("============ Starting stock monitoring job =============");

        List<Article> lowStockProducts = stockMonitorService.findByQuantityLessThan(STOCK_THRESHOLD);
        if (lowStockProducts.isEmpty()) {
            log.info("No products below threshold.");
        } else {
            for (Article article : lowStockProducts) {
                log.warn("Low stock detected for product: {} (Quantity: {}, Threshold: {})",
                        article.getNomArticle(), stockMonitorService.getQuantity(article), STOCK_THRESHOLD);
                stockMonitorService.createPurchaseOrder(article);
            }
        }
        log.info("============ Stock monitoring job finished =============");
    }
}
