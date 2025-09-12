package com.iss4u.erp.services.modules.core.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import com.iss4u.erp.services.modules.core.domain.dto.journal.request.JournalRequest;
import com.iss4u.erp.services.modules.core.domain.dto.journal.response.JournalResponse;
import com.iss4u.erp.services.modules.core.domain.models.Journal;

@Mapper(componentModel = "spring")
public interface JournalMapper {
    JournalResponse toResponse(Journal entity);

    Journal toEntity(JournalRequest dto);

    void updateFromDto(JournalRequest dto, @MappingTarget Journal entity);
}