package com.iss4u.erp.services.modules.achat.domain.commande.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.iss4u.erp.services.modules.achat.domain.commande.models.DevisFournisseur;

public interface DevisFournisseurRepository extends JpaRepository<DevisFournisseur, Long> {
}