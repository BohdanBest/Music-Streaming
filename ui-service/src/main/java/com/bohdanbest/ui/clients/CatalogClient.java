package com.bohdanbest.ui.clients;


import com.bohdanbest.ui.DTO.TrackDto;
import io.quarkus.oidc.token.propagation.common.AccessToken;
import jakarta.ws.rs.*;

import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@RegisterRestClient(configKey = "catalog-api")
@AccessToken
@Path("/tracks")
public interface CatalogClient {

    @GET
    List<TrackDto> search(@QueryParam("artist") String query);
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    void create(TrackDto track);

    @DELETE
    @Path("/{id}")
    void delete(@PathParam("id") Long id);

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void update(@PathParam("id") Long id, TrackDto track);
}
