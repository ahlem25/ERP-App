package com.iss4u.erp.services.controller;

import org.springframework.web.bind.annotation.*;
import com.iss4u.erp.services.service.AppelOffreService;
import com.iss4u.erp.services.modules.achat.domain.commande.dto.appeloffre.request.AppelOffreRequest;
import com.iss4u.erp.services.modules.achat.domain.commande.dto.appeloffre.response.AppelOffreResponse;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/appels-offres")
public class AppelOffreController {

    private final AppelOffreService service;

    @GetMapping
    public List<AppelOffreResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<AppelOffreResponse> findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public AppelOffreResponse create(@RequestBody AppelOffreRequest request) {
        return service.save(request);
    }

    @PutMapping("/{id}")
    public AppelOffreResponse update(
        @PathVariable Long id,
        @RequestBody AppelOffreRequest request
    ) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}