package com.iss4u.erp.services.controller;

import org.springframework.web.bind.annotation.*;
import com.iss4u.erp.services.service.ActiviteService;
import com.iss4u.erp.services.modules.core.domain.dto.activite.request.ActiviteRequest;
import com.iss4u.erp.services.modules.core.domain.dto.activite.response.ActiviteResponse;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/activites")
public class ActiviteController {

    private final ActiviteService service;

    @GetMapping
    public List<ActiviteResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<ActiviteResponse> findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ActiviteResponse create(@RequestBody ActiviteRequest request) {
        return service.save(request);
    }

    @PutMapping("/{id}")
    public ActiviteResponse update(
        @PathVariable Long id,
        @RequestBody ActiviteRequest request
    ) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}