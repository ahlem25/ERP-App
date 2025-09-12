package com.iss4u.erp.services.service;

import org.springframework.stereotype.Service;
import com.iss4u.erp.services.modules.achat.domain.common.repository.RegleTarificationRepository;
import com.iss4u.erp.services.modules.achat.domain.common.mapper.RegleTarificationMapper;
import com.iss4u.erp.services.modules.achat.domain.common.models.RegleTarification;
import com.iss4u.erp.services.modules.achat.domain.common.dto.regletarification.request.RegleTarificationRequest;
import com.iss4u.erp.services.modules.achat.domain.common.dto.regletarification.response.RegleTarificationResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class RegleTarificationService {

    private final RegleTarificationRepository repository;
    private final RegleTarificationMapper mapper;
    
    public List<RegleTarificationResponse> findAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::toResponse)
                         .collect(Collectors.toList());
    }

    public Optional<RegleTarificationResponse> findById(Long id) {
        return repository.findById(id).map(mapper::toResponse);
    }

    public RegleTarificationResponse save(RegleTarificationRequest request) {
        RegleTarification entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    public RegleTarificationResponse update(Long id, RegleTarificationRequest request) {
        RegleTarification existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("RegleTarification not found with id " + id));
        
        mapper.updateFromDto(request, existingEntity);
        
        RegleTarification savedEntity = repository.save(existingEntity);
        return mapper.toResponse(savedEntity);
    }



    public void delete(Long id) {
        repository.deleteById(id);
    }
}