package com.iss4u.erp.services.controller;

import org.springframework.web.bind.annotation.*;
import com.iss4u.erp.services.service.ProfilRoleService;
import com.iss4u.erp.services.modules.core.domain.dto.profilrole.request.ProfilRoleRequest;
import com.iss4u.erp.services.modules.core.domain.dto.profilrole.response.ProfilRoleResponse;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/profil-roles")
public class ProfilRoleController {

    private final ProfilRoleService service;

    @GetMapping
    public List<ProfilRoleResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<ProfilRoleResponse> findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ProfilRoleResponse create(@RequestBody ProfilRoleRequest request) {
        return service.save(request);
    }

    @PutMapping("/{id}")
    public ProfilRoleResponse update(
        @PathVariable Long id,
        @RequestBody ProfilRoleRequest request
    ) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}