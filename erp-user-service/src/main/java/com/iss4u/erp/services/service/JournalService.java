package com.iss4u.erp.services.service;

import org.springframework.stereotype.Service;
import com.iss4u.erp.services.modules.core.domain.repository.JournalRepository;
import com.iss4u.erp.services.modules.core.domain.mapper.JournalMapper;
import com.iss4u.erp.services.modules.core.domain.models.Journal;
import com.iss4u.erp.services.modules.core.domain.dto.journal.request.JournalRequest;
import com.iss4u.erp.services.modules.core.domain.dto.journal.response.JournalResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JournalService {

    private final JournalRepository repository;
    private final JournalMapper mapper;
    
    public List<JournalResponse> findAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::toResponse)
                         .collect(Collectors.toList());
    }

    public Optional<JournalResponse> findById(Long id) {
        return repository.findById(id).map(mapper::toResponse);
    }

    public JournalResponse save(JournalRequest request) {
        Journal entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    public JournalResponse update(Long id, JournalRequest request) {
        Journal existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Journal not found with id " + id));
        
        mapper.updateFromDto(request, existingEntity);
        
        Journal savedEntity = repository.save(existingEntity);
        return mapper.toResponse(savedEntity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}