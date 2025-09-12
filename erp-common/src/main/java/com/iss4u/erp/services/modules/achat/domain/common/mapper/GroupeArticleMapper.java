package com.iss4u.erp.services.modules.achat.domain.common.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import com.iss4u.erp.services.modules.achat.domain.common.dto.groupearticle.request.GroupeArticleRequest;
import com.iss4u.erp.services.modules.achat.domain.common.dto.groupearticle.response.GroupeArticleResponse;
import com.iss4u.erp.services.modules.achat.domain.common.models.GroupeArticle;

@Mapper(componentModel = "spring")
public interface GroupeArticleMapper {
    GroupeArticleResponse toResponse(GroupeArticle entity);

    GroupeArticle toEntity(GroupeArticleRequest dto);

    void updateFromDto(GroupeArticleRequest dto, @MappingTarget GroupeArticle entity);
}