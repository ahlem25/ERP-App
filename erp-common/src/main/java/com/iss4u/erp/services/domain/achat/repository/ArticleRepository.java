package com.iss4u.erp.services.domain.achat.repository;

import com.iss4u.erp.services.domain.achat.models.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}