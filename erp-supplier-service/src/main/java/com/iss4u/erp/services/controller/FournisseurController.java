package com.iss4u.erp.services.controller;

import org.springframework.web.bind.annotation.*;
import com.iss4u.erp.services.service.FournisseurService;
import com.iss4u.erp.services.modules.achat.domain.fournisseur.dto.fournisseur.request.FournisseurRequest;
import com.iss4u.erp.services.modules.achat.domain.fournisseur.dto.fournisseur.response.FournisseurResponse;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/fournisseurs")
public class FournisseurController {

    private final FournisseurService service;

    @GetMapping
    public List<FournisseurResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<FournisseurResponse> findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public FournisseurResponse create(@RequestBody FournisseurRequest request) {
        return service.save(request);
    }

    @PutMapping("/{id}")
    public FournisseurResponse update(
        @PathVariable Long id,
        @RequestBody FournisseurRequest request
    ) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}