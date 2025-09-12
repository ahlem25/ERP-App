package com.iss4u.erp.services.modules.achat.domain.commande.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.iss4u.erp.services.modules.achat.domain.commande.models.FactureAchat;

public interface FactureAchatRepository extends JpaRepository<FactureAchat, Long> {
}