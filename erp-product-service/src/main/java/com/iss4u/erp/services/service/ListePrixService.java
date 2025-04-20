package com.iss4u.erp.services.service;

import org.springframework.stereotype.Service;
import com.iss4u.erp.services.modules.achat.domain.common.repository.ListePrixRepository;
import com.iss4u.erp.services.modules.achat.domain.common.mapper.ListePrixMapper;
import com.iss4u.erp.services.modules.achat.domain.common.models.ListePrix;
import com.iss4u.erp.services.modules.achat.domain.common.dto.listeprix.request.ListePrixRequest;
import com.iss4u.erp.services.modules.achat.domain.common.dto.listeprix.response.ListePrixResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ListePrixService {

    private final ListePrixRepository repository;
    private final ListePrixMapper mapper;

    public List<ListePrixResponse> findAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::toResponse)
                         .collect(Collectors.toList());
    }

    public Optional<ListePrixResponse> findById(Long id) {
        return repository.findById(id).map(mapper::toResponse);
    }

    public ListePrixResponse save(ListePrixRequest request) {
        ListePrix entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    public ListePrixResponse update(Long id, ListePrixRequest request) {
        ListePrix existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("ListePrix not found with id " + id));
        
        mapper.updateFromDto(request, existingEntity);
        
        ListePrix savedEntity = repository.save(existingEntity);
        return mapper.toResponse(savedEntity);
    }



    public void delete(Long id) {
        repository.deleteById(id);
    }
}