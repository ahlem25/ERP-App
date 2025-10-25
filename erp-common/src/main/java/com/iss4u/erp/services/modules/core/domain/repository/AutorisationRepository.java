package com.iss4u.erp.services.modules.core.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.iss4u.erp.services.modules.core.domain.models.Autorisation;

public interface AutorisationRepository extends JpaRepository<Autorisation, Long> {
}