package com.iss4u.erp.services.modules.core.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.iss4u.erp.services.modules.core.domain.models.Commentaire;

public interface CommentaireRepository extends JpaRepository<Commentaire, Long> {
}