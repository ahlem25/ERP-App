package com.iss4u.erp.services.modules.vente.domain.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.iss4u.erp.services.modules.vente.domain.payment.models.Paiement;

public interface PaiementRepository extends JpaRepository<Paiement, Long> {
}