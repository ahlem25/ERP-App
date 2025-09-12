package com.iss4u.erp.services.modules.achat.domain.common.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import com.iss4u.erp.services.modules.achat.domain.common.dto.prixarticle.request.PrixArticleRequest;
import com.iss4u.erp.services.modules.achat.domain.common.dto.prixarticle.response.PrixArticleResponse;
import com.iss4u.erp.services.modules.achat.domain.common.models.PrixArticle;

@Mapper(componentModel = "spring")
public interface PrixArticleMapper {
    PrixArticleResponse toResponse(PrixArticle entity);

    PrixArticle toEntity(PrixArticleRequest dto);

    void updateFromDto(PrixArticleRequest dto, @MappingTarget PrixArticle entity);
}