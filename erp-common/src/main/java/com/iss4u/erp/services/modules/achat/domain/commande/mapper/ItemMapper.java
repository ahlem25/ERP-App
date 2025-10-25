package com.iss4u.erp.services.modules.achat.domain.commande.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import com.iss4u.erp.services.modules.achat.domain.commande.dto.item.request.ItemRequest;
import com.iss4u.erp.services.modules.achat.domain.commande.dto.item.response.ItemResponse;
import com.iss4u.erp.services.modules.achat.domain.commande.models.Item;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    ItemResponse toResponse(Item entity);

    Item toEntity(ItemRequest dto);

    void updateFromDto(ItemRequest dto, @MappingTarget Item entity);
}