package com.iss4u.erp.services.controller;

import org.springframework.web.bind.annotation.*;
import com.iss4u.erp.services.service.EntrepotService;
import com.iss4u.erp.services.modules.stock.domain.dto.entrepot.request.EntrepotRequest;
import com.iss4u.erp.services.modules.stock.domain.dto.entrepot.response.EntrepotResponse;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/entrepots")
public class EntrepotController {

    private final EntrepotService service;

    @GetMapping
    public List<EntrepotResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<EntrepotResponse> findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public EntrepotResponse create(@RequestBody EntrepotRequest request) {
        return service.save(request);
    }

    @PutMapping("/{id}")
    public EntrepotResponse update(
        @PathVariable Long id,
        @RequestBody EntrepotRequest request
    ) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}