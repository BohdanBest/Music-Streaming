package com.bohdanbest.user;


import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
public class UserResource {

    @Inject
    UserRepository userRepository; // Використовуємо Repository Pattern

    @Inject
    SecurityIdentity identity;

    // READ / CREATE (Get current user)
    @GET
    @Path("/me")
    public User getMe() {
        String username = identity.getPrincipal().getName();

        return userRepository.findByUsername(username)
                .orElseGet(() -> {
                    // Якщо юзера немає в БД, створюємо його
                    return createUser(username);
                });
    }

    @Transactional
    public User createUser(String username) {
        User newUser = new User(username, username + "@example.com");
        userRepository.persist(newUser);
        return newUser;
    }

    @PUT
    @Path("/me")
    @Transactional
    public User updateMe(User userUpdate) {
        String username = identity.getPrincipal().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found"));

        // Оновлюємо дозволені поля
        if (userUpdate.email != null) {
            user.email = userUpdate.email;
        }

        return user;
    }

    @DELETE
    @Path("/me")
    @Transactional
    public Response deleteMe() {
        String username = identity.getPrincipal().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found"));

        userRepository.delete(user);

        return Response.noContent().build();
    }
}