package com.iss4u.erp.services.modules.stock.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.iss4u.erp.services.modules.stock.domain.models.SoldeStock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SoldeStockRepository extends JpaRepository<SoldeStock, Long> {
    @Query("SELECT COALESCE(s.quantiteDisponible, 0)FROM SoldeStock s WHERE s.article.id = :articleId")
    Optional<Integer> findStockByArticleId(@Param("articleId") Long articleId);
}