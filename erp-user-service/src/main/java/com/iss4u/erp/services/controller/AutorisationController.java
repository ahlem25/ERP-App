package com.iss4u.erp.services.controller;

import org.springframework.web.bind.annotation.*;
import com.iss4u.erp.services.service.AutorisationService;
import com.iss4u.erp.services.modules.core.domain.dto.autorisation.request.AutorisationRequest;
import com.iss4u.erp.services.modules.core.domain.dto.autorisation.response.AutorisationResponse;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/autorisations")
public class AutorisationController {

    private final AutorisationService service;

    @GetMapping
    public List<AutorisationResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<AutorisationResponse> findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public AutorisationResponse create(@RequestBody AutorisationRequest request) {
        return service.save(request);
    }

    @PutMapping("/{id}")
    public AutorisationResponse update(
        @PathVariable Long id,
        @RequestBody AutorisationRequest request
    ) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}