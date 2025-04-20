package com.iss4u.erp.services.modules.achat.domain.common.repository;

import com.iss4u.erp.services.modules.achat.domain.common.models.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}