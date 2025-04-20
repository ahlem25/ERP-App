package com.iss4u.erp.services.controller;

import org.springframework.web.bind.annotation.*;
import com.iss4u.erp.services.service.RoleService;
import com.iss4u.erp.services.modules.core.domain.dto.role.request.RoleRequest;
import com.iss4u.erp.services.modules.core.domain.dto.role.response.RoleResponse;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/roles")
public class RoleController {

    private final RoleService service;

    @GetMapping
    public List<RoleResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<RoleResponse> findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public RoleResponse create(@RequestBody RoleRequest request) {
        return service.save(request);
    }

    @PutMapping("/{id}")
    public RoleResponse update(
        @PathVariable Long id,
        @RequestBody RoleRequest request
    ) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}