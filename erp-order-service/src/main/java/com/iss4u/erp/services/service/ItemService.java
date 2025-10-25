package com.iss4u.erp.services.service;

import org.springframework.stereotype.Service;
import com.iss4u.erp.services.modules.achat.domain.commande.repository.ItemRepository;
import com.iss4u.erp.services.modules.achat.domain.commande.mapper.ItemMapper;
import com.iss4u.erp.services.modules.achat.domain.commande.models.Item;
import com.iss4u.erp.services.modules.achat.domain.commande.dto.item.request.ItemRequest;
import com.iss4u.erp.services.modules.achat.domain.commande.dto.item.response.ItemResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository repository;
    private final ItemMapper mapper;
    
    public List<ItemResponse> findAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::toResponse)
                         .collect(Collectors.toList());
    }

    public Optional<ItemResponse> findById(Long id) {
        return repository.findById(id).map(mapper::toResponse);
    }

    public ItemResponse save(ItemRequest request) {
        Item entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    public ItemResponse update(Long id, ItemRequest request) {
        Item existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found with id " + id));
        
        mapper.updateFromDto(request, existingEntity);
        
        Item savedEntity = repository.save(existingEntity);
        return mapper.toResponse(savedEntity);
    }



    public void delete(Long id) {
        repository.deleteById(id);
    }
}