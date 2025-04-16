package com.iss4u.erp.services.mapper;

import com.iss4u.erp.services.models.Article;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import com.iss4u.erp.services.dto.article.request.ArticleRequest;
import com.iss4u.erp.services.dto.article.response.ArticleResponse;

@Mapper(componentModel = "spring")
public interface ArticleMapper {
    ArticleResponse toResponse(Article entity);
    Article toEntity(ArticleRequest dto);
    void updateFromDto(ArticleRequest dto, @MappingTarget Article entity);
}