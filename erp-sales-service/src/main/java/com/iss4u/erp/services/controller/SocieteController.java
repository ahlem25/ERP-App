package com.iss4u.erp.services.controller;

import org.springframework.web.bind.annotation.*;
import com.iss4u.erp.services.service.SocieteService;
import com.iss4u.erp.services.modules.vente.domain.sales.dto.societe.request.SocieteRequest;
import com.iss4u.erp.services.modules.vente.domain.sales.dto.societe.response.SocieteResponse;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/societes")
public class SocieteController {

    private final SocieteService service;

    @GetMapping
    public List<SocieteResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<SocieteResponse> findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public SocieteResponse create(@RequestBody SocieteRequest request) {
        return service.save(request);
    }

    @PutMapping("/{id}")
    public SocieteResponse update(
        @PathVariable Long id,
        @RequestBody SocieteRequest request
    ) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}