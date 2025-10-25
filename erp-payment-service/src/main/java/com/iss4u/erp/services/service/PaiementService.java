package com.iss4u.erp.services.service;

import org.springframework.stereotype.Service;
import com.iss4u.erp.services.modules.vente.domain.payment.repository.PaiementRepository;
import com.iss4u.erp.services.modules.vente.domain.payment.mapper.PaiementMapper;
import com.iss4u.erp.services.modules.vente.domain.payment.models.Paiement;
import com.iss4u.erp.services.modules.vente.domain.payment.dto.paiement.request.PaiementRequest;
import com.iss4u.erp.services.modules.vente.domain.payment.dto.paiement.response.PaiementResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaiementService {

    private final PaiementRepository repository;
    private final PaiementMapper mapper;
    
    public List<PaiementResponse> findAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::toResponse)
                         .collect(Collectors.toList());
    }

    public Optional<PaiementResponse> findById(Long id) {
        return repository.findById(id).map(mapper::toResponse);
    }

    public PaiementResponse save(PaiementRequest request) {
        Paiement entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    public PaiementResponse update(Long id, PaiementRequest request) {
        Paiement existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paiement not found with id " + id));
        
        mapper.updateFromDto(request, existingEntity);
        
        Paiement savedEntity = repository.save(existingEntity);
        return mapper.toResponse(savedEntity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}