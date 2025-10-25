package com.iss4u.erp.repository;

import com.iss4u.erp.models.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    
    // Rechercher par email
    Optional<Utilisateur> findByEmail(String email);
    
    // Rechercher par email contenant (insensible à la casse)
    List<Utilisateur> findByEmailContainingIgnoreCase(String email);
    
    // Rechercher par prénom contenant (insensible à la casse)
    List<Utilisateur> findByPrenomContainingIgnoreCase(String prenom);
    
    // Rechercher par nom de famille contenant (insensible à la casse)
    List<Utilisateur> findByNomFamilleContainingIgnoreCase(String nomFamille);
    
    // Recherche combinée par email, prénom ou nom de famille
    @Query("SELECT u FROM Utilisateur u WHERE " +
           "LOWER(u.email) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(u.prenom) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(u.nomFamille) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Utilisateur> findByEmailContainingIgnoreCaseOrPrenomContainingIgnoreCaseOrNomFamilleContainingIgnoreCase(
        @Param("query") String email, 
        @Param("query") String prenom, 
        @Param("query") String nomFamille);
    
    // Rechercher les utilisateurs actifs
    List<Utilisateur> findByActifTrue();
    
    // Rechercher les utilisateurs inactifs
    List<Utilisateur> findByActifFalse();
    
    // Compter les utilisateurs actifs
    long countByActifTrue();
    
    // Compter les utilisateurs inactifs
    long countByActifFalse();
}
