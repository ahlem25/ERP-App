package com.iss4u.erp.services.modules.core.domain.mapper;

import com.iss4u.erp.services.modules.core.domain.dto.commentaire.request.CommentaireRequest;
import com.iss4u.erp.services.modules.core.domain.dto.commentaire.response.CommentaireResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import com.iss4u.erp.services.modules.core.domain.models.Commentaire;

@Mapper(componentModel = "spring")
public interface CommentaireMapper {
    CommentaireResponse toResponse(Commentaire entity);

    Commentaire toEntity(CommentaireRequest dto);

    void updateFromDto(CommentaireRequest dto, @MappingTarget Commentaire entity);
}