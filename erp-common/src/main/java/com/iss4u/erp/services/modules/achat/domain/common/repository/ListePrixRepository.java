package com.iss4u.erp.services.modules.achat.domain.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.iss4u.erp.services.modules.achat.domain.common.models.ListePrix;

public interface ListePrixRepository extends JpaRepository<ListePrix, Long> {
}