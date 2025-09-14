package com.iss4u.erp.services.modules.achat.domain.commande.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.iss4u.erp.services.modules.achat.domain.commande.models.CommandeAchat;

public interface CommandeAchatRepository extends JpaRepository<CommandeAchat, Long> {
    
    /**
     * Counts the number of purchase orders containing a specific article with a given comment
     * @param articleId The article ID
     * @param comment The comment to search for
     * @return The number of orders found
     */
    @Query("SELECT COUNT(DISTINCT c) FROM CommandeAchat c " +
           "JOIN c.articlesCommandes i " +
           "WHERE i.article.id = :articleId " +
           "AND i.commentaire LIKE %:comment%")
    Long countByArticleIdAndComment(@Param("articleId") Long articleId, @Param("comment") String comment);
}