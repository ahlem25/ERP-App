package com.iss4u.erp.services.service;

import org.springframework.stereotype.Service;
import com.iss4u.erp.services.modules.vente.domain.payment.repository.MethodePaiementRepository;
import com.iss4u.erp.services.modules.vente.domain.payment.mapper.MethodePaiementMapper;
import com.iss4u.erp.services.modules.vente.domain.payment.models.MethodePaiement;
import com.iss4u.erp.services.modules.vente.domain.payment.dto.methodepaiement.request.MethodePaiementRequest;
import com.iss4u.erp.services.modules.vente.domain.payment.dto.methodepaiement.response.MethodePaiementResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MethodePaiementService {

    private final MethodePaiementRepository repository;
    private final MethodePaiementMapper mapper;
    
    public List<MethodePaiementResponse> findAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::toResponse)
                         .collect(Collectors.toList());
    }

    public Optional<MethodePaiementResponse> findById(Long id) {
        return repository.findById(id).map(mapper::toResponse);
    }

    public MethodePaiementResponse save(MethodePaiementRequest request) {
        MethodePaiement entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    public MethodePaiementResponse update(Long id, MethodePaiementRequest request) {
        MethodePaiement existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("MethodePaiement not found with id " + id));
        
        mapper.updateFromDto(request, existingEntity);
        
        MethodePaiement savedEntity = repository.save(existingEntity);
        return mapper.toResponse(savedEntity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}