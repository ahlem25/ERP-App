package com.iss4u.erp.services.modules.achat.domain.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.iss4u.erp.services.modules.achat.domain.common.models.RegleTarification;

public interface RegleTarificationRepository extends JpaRepository<RegleTarification, Long> {
}