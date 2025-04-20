package com.iss4u.erp.services.controller;

import org.springframework.web.bind.annotation.*;
import com.iss4u.erp.services.service.BonLivraisonService;
import com.iss4u.erp.services.modules.stock.domain.dto.bonlivraison.request.BonLivraisonRequest;
import com.iss4u.erp.services.modules.stock.domain.dto.bonlivraison.response.BonLivraisonResponse;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bon-livraisons")
public class BonLivraisonController {

    private final BonLivraisonService service;

    @GetMapping
    public List<BonLivraisonResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<BonLivraisonResponse> findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public BonLivraisonResponse create(@RequestBody BonLivraisonRequest request) {
        return service.save(request);
    }

    @PutMapping("/{id}")
    public BonLivraisonResponse update(
        @PathVariable Long id,
        @RequestBody BonLivraisonRequest request
    ) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}