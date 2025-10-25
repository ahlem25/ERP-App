package com.iss4u.erp.services.modules.vente.domain.billing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.iss4u.erp.services.modules.vente.domain.billing.models.FactureVente;

public interface FactureVenteRepository extends JpaRepository<FactureVente, Long> {
}