package com.bohdanbest.catalog;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/tracks")
@Produces(MediaType.APPLICATION_JSON)
public class CatalogResource {

    @Inject
    InMemoryCatalogRepository catalogRepository;

    @GET
    public List<Track> getAllTracks() {
        return catalogRepository.findAll();
    }

    @GET
    @Path("/{id}")
    public Track getTrackById(@PathParam("id") Long id) {
        return catalogRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Track not found"));
    }
}
