package com.bohdanbest.catalog;

import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/tracks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
public class CatalogResource {

    @GET
    public List<Track> getAll(@QueryParam("artist") String artist) {
        if (artist != null && !artist.isBlank()) {
            return Track.findByArtist(artist);
        }
        return Track.listAll();
    }

    @POST
    @Transactional
    @RolesAllowed("admin") // Тільки адмін може додавати через REST
    public Track add(Track track) {
        track.persist();
        return track;
    }

    @GET
    @Path("/{id}")
    public Track getById(@PathParam("id") Long id) {
        return Track.findById(id);
    }
}
