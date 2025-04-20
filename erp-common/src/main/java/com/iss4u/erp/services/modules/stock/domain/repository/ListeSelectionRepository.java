package com.iss4u.erp.services.modules.stock.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.iss4u.erp.services.modules.stock.domain.models.ListeSelection;

public interface ListeSelectionRepository extends JpaRepository<ListeSelection, Long> {
}