package com.iss4u.erp.services.service;

import org.springframework.stereotype.Service;
import com.iss4u.erp.services.modules.core.domain.repository.DocumentRepository;
import com.iss4u.erp.services.modules.core.domain.mapper.DocumentMapper;
import com.iss4u.erp.services.modules.core.domain.models.Document;
import com.iss4u.erp.services.modules.core.domain.dto.document.request.DocumentRequest;
import com.iss4u.erp.services.modules.core.domain.dto.document.response.DocumentResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository repository;
    private final DocumentMapper mapper;
    
    public List<DocumentResponse> findAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::toResponse)
                         .collect(Collectors.toList());
    }

    public Optional<DocumentResponse> findById(Long id) {
        return repository.findById(id).map(mapper::toResponse);
    }

    public DocumentResponse save(DocumentRequest request) {
        Document entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    public DocumentResponse update(Long id, DocumentRequest request) {
        Document existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found with id " + id));
        
        mapper.updateFromDto(request, existingEntity);
        
        Document savedEntity = repository.save(existingEntity);
        return mapper.toResponse(savedEntity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}