package com.iss4u.erp.services.modules.achat.domain.fournisseur.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.iss4u.erp.services.modules.achat.domain.fournisseur.models.Fournisseur;

public interface FournisseurRepository extends JpaRepository<Fournisseur, Long> {
}