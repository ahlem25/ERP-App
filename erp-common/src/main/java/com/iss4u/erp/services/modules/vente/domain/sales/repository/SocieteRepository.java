package com.iss4u.erp.services.modules.vente.domain.sales.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.iss4u.erp.services.modules.vente.domain.sales.models.Societe;

public interface SocieteRepository extends JpaRepository<Societe, Long> {
}