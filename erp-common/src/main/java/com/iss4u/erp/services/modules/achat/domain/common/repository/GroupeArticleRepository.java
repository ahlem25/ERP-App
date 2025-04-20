package com.iss4u.erp.services.modules.achat.domain.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.iss4u.erp.services.modules.achat.domain.common.models.GroupeArticle;

public interface GroupeArticleRepository extends JpaRepository<GroupeArticle, Long> {
}