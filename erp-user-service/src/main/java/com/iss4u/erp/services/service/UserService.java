package com.iss4u.erp.services.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

//    @Autowired
//    private Keycloak keycloak;
//
//    @Value("${keycloak.realm}")
//    private String realm;

/*    public void createUser(String username, String email, String password) {
        UserRepresentation user = new UserRepresentation();
        user.setUsername(username);
        user.setEmail(email);
        user.setEnabled(true);

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(password);
        credential.setTemporary(false);

        user.setCredentials(List.of(credential));

        keycloak.realm(realm).users().create(user);
    }*/

/*    public void assignRole(String userId, String roleName) {
        RoleRepresentation role = keycloak.realm(realm).roles().get(roleName).toRepresentation();
        keycloak.realm(realm).users().get(userId).roles().realmLevel().add(List.of(role));
    }*/

 /*   public List<UserRepresentation> listUsers() {
        return keycloak.realm(realm).users().list();
    }*/
}
