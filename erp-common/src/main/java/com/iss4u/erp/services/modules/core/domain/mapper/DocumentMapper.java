package com.iss4u.erp.services.modules.core.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import com.iss4u.erp.services.modules.core.domain.dto.document.request.DocumentRequest;
import com.iss4u.erp.services.modules.core.domain.dto.document.response.DocumentResponse;
import com.iss4u.erp.services.modules.core.domain.models.Document;

@Mapper(componentModel = "spring")
public interface DocumentMapper {
    DocumentResponse toResponse(Document entity);

    Document toEntity(DocumentRequest dto);

    void updateFromDto(DocumentRequest dto, @MappingTarget Document entity);
}