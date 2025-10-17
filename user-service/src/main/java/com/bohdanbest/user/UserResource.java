package com.bohdanbest.user;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    InMemoryUserRepository userRepository;

    @GET
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
