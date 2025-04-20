package com.iss4u.erp.services.controller;

import org.springframework.web.bind.annotation.*;
import com.iss4u.erp.services.service.ProfilPDVService;
import com.iss4u.erp.services.modules.vente.domain.sales.dto.profilpdv.request.ProfilPDVRequest;
import com.iss4u.erp.services.modules.vente.domain.sales.dto.profilpdv.response.ProfilPDVResponse;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/profils-pdv")
public class ProfilPDVController {

    private final ProfilPDVService service;

    @GetMapping
    public List<ProfilPDVResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<ProfilPDVResponse> findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ProfilPDVResponse create(@RequestBody ProfilPDVRequest request) {
        return service.save(request);
    }

    @PutMapping("/{id}")
    public ProfilPDVResponse update(
        @PathVariable Long id,
        @RequestBody ProfilPDVRequest request
    ) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}