package com.iss4u.erp.services.modules.stock.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.iss4u.erp.services.modules.stock.domain.models.LivreInventaire;
import java.util.List;
import java.util.Optional;

public interface LivreInventaireRepository extends JpaRepository<LivreInventaire, Long> {
    
    @Query("SELECT li FROM LivreInventaire li " +
           "LEFT JOIN FETCH li.article " +
           "LEFT JOIN FETCH li.entrepot " +
           "LEFT JOIN FETCH li.lot " +
           "LEFT JOIN FETCH li.mouvement " +
           "LEFT JOIN FETCH li.numeroSerie")
    List<LivreInventaire> findAllWithRelations();
    
    @Query("SELECT li FROM LivreInventaire li " +
           "LEFT JOIN FETCH li.article " +
           "LEFT JOIN FETCH li.entrepot " +
           "LEFT JOIN FETCH li.lot " +
           "LEFT JOIN FETCH li.mouvement " +
           "LEFT JOIN FETCH li.numeroSerie " +
           "WHERE li.id = :id")
    Optional<LivreInventaire> findByIdWithRelations(@Param("id") Long id);
}