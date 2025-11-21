package com.bohdanbest.user;

import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Authenticated
public class UserResource {

    @Inject
    UserRepository userRepository; // Інжектуємо Репозиторій

    @Inject
    SecurityIdentity identity;

    @GET
    @Path("/me")
    public User getMe() {
        String username = identity.getPrincipal().getName();

        return userRepository.findByUsername(username)
                .orElseGet(() -> {
                    return createUser(username);
                });
    }

    @Transactional
    public User createUser(String username) {
        User newUser = new User(username, username + "@example.com");
        userRepository.persist(newUser);
        return newUser;
    }
}