package com.iss4u.erp.services.controller;

import org.springframework.web.bind.annotation.*;
import com.iss4u.erp.services.service.OpportuniteService;
import com.iss4u.erp.services.modules.vente.domain.sales.dto.opportunite.request.OpportuniteRequest;
import com.iss4u.erp.services.modules.vente.domain.sales.dto.opportunite.response.OpportuniteResponse;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/opportunites")
public class OpportuniteController {

    private final OpportuniteService service;

    @GetMapping
    public List<OpportuniteResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<OpportuniteResponse> findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public OpportuniteResponse create(@RequestBody OpportuniteRequest request) {
        return service.save(request);
    }

    @PutMapping("/{id}")
    public OpportuniteResponse update(
        @PathVariable Long id,
        @RequestBody OpportuniteRequest request
    ) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}