package com.iss4u.erp.services.controller;

import org.springframework.web.bind.annotation.*;
import com.iss4u.erp.services.service.ListeSelectionService;
import com.iss4u.erp.services.modules.stock.domain.dto.listeselection.request.ListeSelectionRequest;
import com.iss4u.erp.services.modules.stock.domain.dto.listeselection.response.ListeSelectionResponse;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/listes-selection")
public class ListeSelectionController {

    private final ListeSelectionService service;

    @GetMapping
    public List<ListeSelectionResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<ListeSelectionResponse> findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ListeSelectionResponse create(@RequestBody ListeSelectionRequest request) {
        return service.save(request);
    }

    @PutMapping("/{id}")
    public ListeSelectionResponse update(
        @PathVariable Long id,
        @RequestBody ListeSelectionRequest request
    ) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}