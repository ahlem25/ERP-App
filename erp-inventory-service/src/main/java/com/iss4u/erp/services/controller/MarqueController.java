package com.iss4u.erp.services.controller;

import org.springframework.web.bind.annotation.*;
import com.iss4u.erp.services.service.MarqueService;
import com.iss4u.erp.services.modules.stock.domain.dto.marque.request.MarqueRequest;
import com.iss4u.erp.services.modules.stock.domain.dto.marque.response.MarqueResponse;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/marques")
public class MarqueController {

    private final MarqueService service;

    @GetMapping
    public List<MarqueResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<MarqueResponse> findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public MarqueResponse create(@RequestBody MarqueRequest request) {
        return service.save(request);
    }

    @PutMapping("/{id}")
    public MarqueResponse update(
        @PathVariable Long id,
        @RequestBody MarqueRequest request
    ) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}