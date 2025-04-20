package com.iss4u.erp.services.modules.achat.domain.fournisseur.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.iss4u.erp.services.modules.achat.domain.fournisseur.models.GroupeFournisseurs;

public interface GroupeFournisseursRepository extends JpaRepository<GroupeFournisseurs, Long> {
}