package com.iss4u.erp.services.service;

import org.springframework.stereotype.Service;
import com.iss4u.erp.services.modules.stock.domain.repository.ListeSelectionRepository;
import com.iss4u.erp.services.modules.stock.domain.mapper.ListeSelectionMapper;
import com.iss4u.erp.services.modules.stock.domain.models.ListeSelection;
import com.iss4u.erp.services.modules.stock.domain.dto.listeselection.request.ListeSelectionRequest;
import com.iss4u.erp.services.modules.stock.domain.dto.listeselection.response.ListeSelectionResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListeSelectionService {

    private final ListeSelectionRepository repository;
    private final ListeSelectionMapper mapper;
    
    public List<ListeSelectionResponse> findAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::toResponse)
                         .collect(Collectors.toList());
    }

    public Optional<ListeSelectionResponse> findById(Long id) {
        return repository.findById(id).map(mapper::toResponse);
    }

    public ListeSelectionResponse save(ListeSelectionRequest request) {
        ListeSelection entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    public ListeSelectionResponse update(Long id, ListeSelectionRequest request) {
        ListeSelection existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("ListeSelection not found with id " + id));
        
        mapper.updateFromDto(request, existingEntity);
        
        ListeSelection savedEntity = repository.save(existingEntity);
        return mapper.toResponse(savedEntity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}