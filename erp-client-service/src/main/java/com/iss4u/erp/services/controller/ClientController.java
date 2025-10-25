package com.iss4u.erp.services.controller;

import org.springframework.web.bind.annotation.*;
import com.iss4u.erp.services.service.ClientService;
import com.iss4u.erp.services.modules.vente.domain.client.dto.client.request.ClientRequest;
import com.iss4u.erp.services.modules.vente.domain.client.dto.client.response.ClientResponse;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/clients")
public class ClientController {

    private final ClientService service;

    @GetMapping
    public List<ClientResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<ClientResponse> findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ClientResponse create(@RequestBody ClientRequest request) {
        return service.save(request);
    }

    @PutMapping("/{id}")
    public ClientResponse update(
        @PathVariable Long id,
        @RequestBody ClientRequest request
    ) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
