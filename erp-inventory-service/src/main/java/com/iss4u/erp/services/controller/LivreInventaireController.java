package com.iss4u.erp.services.controller;

import org.springframework.web.bind.annotation.*;
import com.iss4u.erp.services.service.LivreInventaireService;
import com.iss4u.erp.services.modules.stock.domain.dto.livreinventaire.request.LivreInventaireRequest;
import com.iss4u.erp.services.modules.stock.domain.dto.livreinventaire.response.LivreInventaireResponse;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/livres-inventaire")
public class LivreInventaireController {

    private final LivreInventaireService service;

    @GetMapping
    public List<LivreInventaireResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<LivreInventaireResponse> findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public LivreInventaireResponse create(@RequestBody LivreInventaireRequest request) {
        return service.save(request);
    }

    @PutMapping("/{id}")
    public LivreInventaireResponse update(
        @PathVariable Long id,
        @RequestBody LivreInventaireRequest request
    ) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}