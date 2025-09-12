package com.iss4u.erp.services.controller;

import org.springframework.web.bind.annotation.*;
import com.iss4u.erp.services.service.UtilisateurService;
import com.iss4u.erp.services.modules.core.domain.dto.utilisateur.request.UtilisateurRequest;
import com.iss4u.erp.services.modules.core.domain.dto.utilisateur.response.UtilisateurResponse;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/utilisateurs")
public class UtilisateurController {

    private final UtilisateurService service;

    @GetMapping
    public List<UtilisateurResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<UtilisateurResponse> findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public UtilisateurResponse create(@RequestBody UtilisateurRequest request) {
        return service.save(request);
    }

    @PutMapping("/{id}")
    public UtilisateurResponse update(
        @PathVariable Long id,
        @RequestBody UtilisateurRequest request
    ) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}