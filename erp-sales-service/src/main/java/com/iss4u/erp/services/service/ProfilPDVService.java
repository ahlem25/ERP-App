package com.iss4u.erp.services.service;

import org.springframework.stereotype.Service;
import com.iss4u.erp.services.modules.vente.domain.sales.repository.ProfilPDVRepository;
import com.iss4u.erp.services.modules.vente.domain.sales.mapper.ProfilPDVMapper;
import com.iss4u.erp.services.modules.vente.domain.sales.models.ProfilPDV;
import com.iss4u.erp.services.modules.vente.domain.sales.dto.profilpdv.request.ProfilPDVRequest;
import com.iss4u.erp.services.modules.vente.domain.sales.dto.profilpdv.response.ProfilPDVResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfilPDVService {

    private final ProfilPDVRepository repository;
    private final ProfilPDVMapper mapper;
    
    public List<ProfilPDVResponse> findAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::toResponse)
                         .collect(Collectors.toList());
    }

    public Optional<ProfilPDVResponse> findById(Long id) {
        return repository.findById(id).map(mapper::toResponse);
    }

    public ProfilPDVResponse save(ProfilPDVRequest request) {
        ProfilPDV entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    public ProfilPDVResponse update(Long id, ProfilPDVRequest request) {
        ProfilPDV existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("ProfilPDV not found with id " + id));
        
        mapper.updateFromDto(request, existingEntity);
        
        ProfilPDV savedEntity = repository.save(existingEntity);
        return mapper.toResponse(savedEntity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}