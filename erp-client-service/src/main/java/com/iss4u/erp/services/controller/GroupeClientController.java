package com.iss4u.erp.services.controller;

import org.springframework.web.bind.annotation.*;
import com.iss4u.erp.services.service.GroupeClientService;
import com.iss4u.erp.services.modules.vente.domain.client.dto.groupeclient.request.GroupeClientRequest;
import com.iss4u.erp.services.modules.vente.domain.client.dto.groupeclient.response.GroupeClientResponse;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/groupe-clients")
public class GroupeClientController {

    private final GroupeClientService service;

    @GetMapping
    public List<GroupeClientResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<GroupeClientResponse> findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public GroupeClientResponse create(@RequestBody GroupeClientRequest request) {
        return service.save(request);
    }

    @PutMapping("/{id}")
    public GroupeClientResponse update(
        @PathVariable Long id,
        @RequestBody GroupeClientRequest request
    ) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
