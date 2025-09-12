package com.iss4u.erp.services.modules.stock.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.iss4u.erp.services.modules.stock.domain.models.NumeroSerie;

public interface NumeroSerieRepository extends JpaRepository<NumeroSerie, Long> {
}