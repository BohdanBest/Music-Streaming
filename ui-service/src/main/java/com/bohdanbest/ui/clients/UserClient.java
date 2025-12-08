package com.bohdanbest.ui.clients;


import com.bohdanbest.ui.DTO.UserDto;
import io.quarkus.oidc.token.propagation.common.AccessToken;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "user-api")
@AccessToken
@Path("/users")
public interface UserClient {

    @GET
    @Path("/me")
    UserDto getMe();
}
