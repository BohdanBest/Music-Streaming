package com.bohdanbest.playlist.client;

import io.quarkus.oidc.token.propagation.common.AccessToken;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "catalog-api")
@AccessToken
@Path("/tracks")
public interface CatalogClient {

    @GET
    @Path("/{id}")
    Object getTrack(@PathParam("id") Long id);
}
