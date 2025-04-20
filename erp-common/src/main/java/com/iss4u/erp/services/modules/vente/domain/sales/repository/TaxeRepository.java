package com.iss4u.erp.services.modules.vente.domain.sales.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.iss4u.erp.services.modules.vente.domain.sales.models.Taxe;

public interface TaxeRepository extends JpaRepository<Taxe, Long> {
}