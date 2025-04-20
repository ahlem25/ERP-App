package com.iss4u.erp.services.controller;

import org.springframework.web.bind.annotation.*;
import com.iss4u.erp.services.service.GroupeFournisseursService;
import com.iss4u.erp.services.modules.achat.domain.fournisseur.dto.groupefournisseurs.request.GroupeFournisseursRequest;
import com.iss4u.erp.services.modules.achat.domain.fournisseur.dto.groupefournisseurs.response.GroupeFournisseursResponse;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/groupe-fournisseurs")
public class GroupeFournisseursController {
    private final GroupeFournisseursService service;

    @GetMapping
    public List<GroupeFournisseursResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<GroupeFournisseursResponse> findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public GroupeFournisseursResponse create(@RequestBody GroupeFournisseursRequest request) {
        return service.save(request);
    }

    @PutMapping("/{id}")
    public GroupeFournisseursResponse update(
        @PathVariable Long id,
        @RequestBody GroupeFournisseursRequest request
    ) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}