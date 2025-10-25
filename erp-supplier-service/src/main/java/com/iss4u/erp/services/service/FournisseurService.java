package com.iss4u.erp.services.service;

import org.springframework.stereotype.Service;
import com.iss4u.erp.services.modules.achat.domain.fournisseur.repository.FournisseurRepository;
import com.iss4u.erp.services.modules.achat.domain.fournisseur.mapper.FournisseurMapper;
import com.iss4u.erp.services.modules.achat.domain.fournisseur.models.Fournisseur;
import com.iss4u.erp.services.modules.achat.domain.fournisseur.dto.fournisseur.request.FournisseurRequest;
import com.iss4u.erp.services.modules.achat.domain.fournisseur.dto.fournisseur.response.FournisseurResponse;
import com.iss4u.erp.services.modules.achat.domain.fournisseur.repository.GroupeFournisseursRepository;
import com.iss4u.erp.services.modules.achat.domain.common.repository.ListePrixRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FournisseurService {
    private final FournisseurRepository repository;
    private final FournisseurMapper mapper;
    private final GroupeFournisseursRepository groupeFournisseursRepository;
    private final ListePrixRepository listePrixRepository;
    
    public List<FournisseurResponse> findAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::toResponse)
                         .collect(Collectors.toList());
    }

    public Optional<FournisseurResponse> findById(Long id) {
        return repository.findById(id).map(mapper::toResponse);
    }

    public FournisseurResponse save(FournisseurRequest request) {
        Fournisseur entity = new Fournisseur();
        updateEntityFromRequest(entity, request);
        return mapper.toResponse(repository.save(entity));
    }

    public FournisseurResponse update(Long id, FournisseurRequest request) {
        Fournisseur existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fournisseur not found with id " + id));
        
        updateEntityFromRequest(existingEntity, request);
        
        Fournisseur savedEntity = repository.save(existingEntity);
        return mapper.toResponse(savedEntity);
    }

    private void updateEntityFromRequest(Fournisseur entity, FournisseurRequest request) {
        entity.setNomFournisseur(request.getNomFournisseur());
        entity.setPays(request.getPays());
        entity.setTypeFournisseur(request.getTypeFournisseur());
        entity.setEstTransporteur(request.isEstTransporteur());
        entity.setDeviseFacturation(request.getDeviseFacturation());
        entity.setCompteBancaireParDefaut(request.getCompteBancaireParDefaut());
        entity.setEstFournisseurInterne(request.isEstFournisseurInterne());
        entity.setDetailsFournisseur(request.getDetailsFournisseur());
        entity.setSiteWeb(request.getSiteWeb());
        entity.setLangueImpression(request.getLangueImpression());

        if (request.getGroupeDeFournisseursId() != null) {
            entity.setGroupeDeFournisseurs(
                groupeFournisseursRepository.findById(request.getGroupeDeFournisseursId())
                    .orElseThrow(() -> new RuntimeException("GroupeFournisseurs not found with id " + request.getGroupeDeFournisseursId()))
            );
        }

        if (request.getListeDePrixId() != null) {
            entity.setListeDePrix(
                listePrixRepository.findById(request.getListeDePrixId())
                    .orElseThrow(() -> new RuntimeException("ListePrix not found with id " + request.getListeDePrixId()))
            );
        }
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}