package com.iss4u.erp.services.modules.vente.domain.client.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.iss4u.erp.services.modules.vente.domain.client.models.GroupeClient;

public interface GroupeClientRepository extends JpaRepository<GroupeClient, Long> {
}