package com.iss4u.erp.services.controller;


import com.iss4u.erp.services.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

/*    @GetMapping
    public List<UserRepresentation> findAll() {
        return userService.listUsers();
    }*/

    @PostMapping("/create")
    public void createUser() {
        //userService.createUser(username, email, password);
        //return ResponseEntity.ok().build();
    }

/*    @PostMapping("/assign-role")
    public ResponseEntity<Void> assignRole(@RequestParam String userId,
                                           @RequestParam String roleName) {
        userService.assignRole(userId, roleName);
        return ResponseEntity.ok().build();
    }*/
}
