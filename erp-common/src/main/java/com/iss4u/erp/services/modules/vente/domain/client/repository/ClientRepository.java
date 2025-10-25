package com.iss4u.erp.services.modules.vente.domain.client.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.iss4u.erp.services.modules.vente.domain.client.models.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
