package com.iss4u.erp.services.modules.vente.domain.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.iss4u.erp.services.modules.vente.domain.payment.models.MethodePaiement;

public interface MethodePaiementRepository extends JpaRepository<MethodePaiement, Long> {
}