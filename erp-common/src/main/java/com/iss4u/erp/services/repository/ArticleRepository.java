package com.iss4u.erp.services.repository;

import com.iss4u.erp.services.models.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}