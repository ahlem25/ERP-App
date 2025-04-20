package com.iss4u.erp.services.modules.achat.domain.commande.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.iss4u.erp.services.modules.achat.domain.commande.models.CommandeAchat;

public interface CommandeAchatRepository extends JpaRepository<CommandeAchat, Long> {
}