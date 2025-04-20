package com.iss4u.erp.services.modules.vente.domain.sales.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.iss4u.erp.services.modules.vente.domain.sales.models.Vendeur;

public interface VendeurRepository extends JpaRepository<Vendeur, Long> {
}