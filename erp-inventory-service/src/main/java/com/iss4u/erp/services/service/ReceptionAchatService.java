package com.iss4u.erp.services.service;

import org.springframework.stereotype.Service;
import com.iss4u.erp.services.modules.stock.domain.repository.ReceptionAchatRepository;
import com.iss4u.erp.services.modules.stock.domain.mapper.ReceptionAchatMapper;
import com.iss4u.erp.services.modules.stock.domain.models.ReceptionAchat;
import com.iss4u.erp.services.modules.stock.domain.dto.receptionachat.request.ReceptionAchatRequest;
import com.iss4u.erp.services.modules.stock.domain.dto.receptionachat.response.ReceptionAchatResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class ReceptionAchatService {

    private final ReceptionAchatRepository repository;
    private final ReceptionAchatMapper mapper;
    
    public List<ReceptionAchatResponse> findAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::toResponse)
                         .collect(Collectors.toList());
    }

    public Optional<ReceptionAchatResponse> findById(Long id) {
        return repository.findById(id).map(mapper::toResponse);
    }

    public ReceptionAchatResponse save(ReceptionAchatRequest request) {
        ReceptionAchat entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    public ReceptionAchatResponse update(Long id, ReceptionAchatRequest request) {
        ReceptionAchat existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("ReceptionAchat not found with id " + id));
        
        mapper.updateFromDto(request, existingEntity);
        
        ReceptionAchat savedEntity = repository.save(existingEntity);
        return mapper.toResponse(savedEntity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}