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
    @RolesAllowed("admin")
    public Track add(Track track) {
        track.persist();
        return track;
    }

    @GET
    @Path("/{id}")
    public Track getById(@PathParam("id") Long id) {
        return Track.findById(id);
    }
    @PUT
    @Path("/{id}")
    @Transactional
    @RolesAllowed("admin")
    public Track update(@PathParam("id") Long id, Track newTrack) {
        Track entity = Track.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }

        entity.title = newTrack.title;
        entity.artist = newTrack.artist;
        entity.album = newTrack.album;

        return entity;
    }

    @GET
    @Path("/recommendations")
    public List<Track> getRecommendations() {
        return Track.findAll().list();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @RolesAllowed("admin")
    public void delete(@PathParam("id") Long id) {
        boolean deleted = Track.deleteById(id);
        if (!deleted) {
            throw new NotFoundException();
        }
    }
}
