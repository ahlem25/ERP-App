package com.iss4u.erp.services.modules.stock.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.iss4u.erp.services.modules.stock.domain.models.SoldeStock;

public interface SoldeStockRepository extends JpaRepository<SoldeStock, Long> {
}